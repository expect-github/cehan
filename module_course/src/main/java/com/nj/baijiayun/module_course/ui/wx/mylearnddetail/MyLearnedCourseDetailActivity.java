package com.nj.baijiayun.module_course.ui.wx.mylearnddetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baijiayun.videoplayer.ui.event.UIEventKey;
import com.lzf.easyfloat.permission.PermissionUtils;
import com.nj.baijiayun.basic.utils.ClickUtils;
import com.nj.baijiayun.basic.utils.ToastUtil;
import com.nj.baijiayun.downloader.DownloadManager;
import com.nj.baijiayun.downloader.realmbean.DownloadItem;
import com.nj.baijiayun.module_common.base.BaseAppActivity;
import com.nj.baijiayun.module_common.di.qualifier.Qualifier1;
import com.nj.baijiayun.module_common.di.qualifier.Qualifier2;
import com.nj.baijiayun.module_common.helper.BJYDialogHelper;
import com.nj.baijiayun.module_common.helper.ToolBarHelper;
import com.nj.baijiayun.module_common.widget.dialog.CommonMDDialog;
import com.nj.baijiayun.module_course.R;
import com.nj.baijiayun.module_course.adapter.my_course.MyLearnedCourseSectionHolder;
import com.nj.baijiayun.module_course.bean.wx.ChapterBean;
import com.nj.baijiayun.module_course.bean.wx.MyLearnedDetailWrapperBean;
import com.nj.baijiayun.module_course.bean.wx.SectionBean;
import com.nj.baijiayun.module_course.helper.CourseHelper;
import com.nj.baijiayun.module_course.widget.CourseUndercarriageDialog;
import com.nj.baijiayun.module_public.consts.ConstsCouseType;
import com.nj.baijiayun.module_public.consts.RouterConstant;
import com.nj.baijiayun.module_public.helper.JumpHelper;
import com.nj.baijiayun.module_public.helper.ViewHelper;
import com.nj.baijiayun.module_public.manager.VideoUploadRecordManager;
import com.nj.baijiayun.processor.Module_courseAdapterHelper;
import com.nj.baijiayun.refresh.recycleview.BaseMultipleTypeRvAdapter;
import com.nj.baijiayun.refresh.recycleview.ExpandHelper;
import com.nj.baijiayun.refresh.recycleview.SpaceItemDecoration;
import com.nj.baijiayun.sdk_player.manager.BjyVideoPlayManager;
import com.nj.baijiayun.sdk_player.manager.VideoHelper;
import com.nj.baijiayun.sdk_player.ui.FullScreenVideoPlayActivity;
import com.nj.baijiayun.sdk_player.widget.NjVideoView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * @author chengang
 * @date 2019-08-04
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.module_course.ui.wx.mycourse
 * @describe
 * 
 */

@Route(path = RouterConstant.PAGE_COURSE_MY_LEARNED_DETAIL)
public class MyLearnedCourseDetailActivity extends BaseAppActivity<MyLearnedDetailContract.Presenter> implements MyLearnedDetailContract.View {
    private TextView mSectionsTv;
    private ProgressBar mPgLearned;
    private TextView mLearnedTv;
    private RecyclerView mRv;
    private View mCommentCl;
    private View mCourseDetailCl;
    private View mRemoveListCl;
    private BaseMultipleTypeRvAdapter rvAdapter;
    private TextView mLearnQrcodeTv;
    private LinearLayout mLearnQrcodeCl;
    /**
     * 视频播放相关
     */
    private NjVideoView mPlvVideo;
    private String mLastPlayVideoId = "0";
    private String mLastPlayToken = "";
    @Qualifier1
    @Inject
    public int mCourseId;
    @Qualifier2
    @Inject
    public int mCourseType;


    @Override
    protected int bindContentViewLayoutId() {
        return R.layout.course_activity_my_learned_detail;

    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView(Bundle savedInstanceState) {
        showContentView();
        initProgressUi();
        initRv();
        initBottomUi();
        initPlayerUi();
        ToolBarHelper.addRightImageView(getToolBar()
                , R.drawable.public_ic_calendar,23
                , v -> ARouter.getInstance().build(RouterConstant.PAGE_COURSE_LEARN_CALENDAR).navigation());
        //防止遮住图标
        ToolBarHelper.setTitlePaddingLeftRight(getTitleTextView());
    }

    private void initPlayerUi() {
        mPlvVideo = findViewById(R.id.plv_video);
        findViewById(R.id.rel_learn_pgr_parent).setVisibility(CourseHelper.isShowProgress(mCourseType) ? View.VISIBLE : View.GONE);
        findViewById(R.id.rl_player).setVisibility(CourseHelper.isShowPlayer(mCourseType) ? View.VISIBLE : View.GONE);
        initPlayer();
    }

    private void initProgressUi() {
        mSectionsTv = findViewById(R.id.tv_sections);
        mPgLearned = findViewById(R.id.pg_learned);
        mLearnedTv = findViewById(R.id.tv_learned);
    }

    private void initBottomUi() {
        mCommentCl = findViewById(R.id.cl_comment);
        mCourseDetailCl = findViewById(R.id.cl_course_detail);
        mRemoveListCl = findViewById(R.id.cl_remove_list);
        mLearnQrcodeTv = findViewById(R.id.tv_learn_qrcode);
        mLearnQrcodeCl = findViewById(R.id.cl_learn_qrcode);
    }

    private void initPlayer() {
        mPlvVideo.initPlayer(BjyVideoPlayManager.getMediaPlayer(this));
        mPlvVideo.hideBackIcon();
        mPlvVideo.getPlayer().stop();
        VideoHelper.resetRenderTypeTexture(mPlvVideo);
        mPlvVideo.setComponentEventListener((eventCode, bundle) -> {
            if (eventCode == UIEventKey.CUSTOM_CODE_REQUEST_TOGGLE_SCREEN) {
                startActivity(new Intent(getActivity(), FullScreenVideoPlayActivity.class));
            } else if (eventCode == UIEventKey.CUSTOM_CODE_REQUEST_BACK) {
                onBackPressedSupport();
            }
        });
        mPlvVideo.setVideoSizeCallBack(VideoHelper::setCurrentPlayVideoInfo);
        ViewHelper.setViewVisibleInVisivle(mPlvVideo, mPresenter.isNeedPlayer());
        VideoUploadRecordManager.getInstance().setPlayer(mPlvVideo.getPlayer());

    }

    private void initRv() {
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvAdapter = Module_courseAdapterHelper.getLearneddetailAdapter(this);
        mRv.setAdapter(rvAdapter);
    }

    int count = 0;

    @Override
    protected void onResume() {
        super.onResume();
        if (count > 0) {
            if (getPresenter() != null && getPresenter().isNeedPlayer()) {
                if (BjyVideoPlayManager.isReleased()) {
                    mPlvVideo.initPlayer(BjyVideoPlayManager.getMediaPlayer(this));
                    mPlvVideo.getPlayer().setupOnlineVideoWithId(Long.parseLong(mLastPlayVideoId),mLastPlayToken);
                }else {
                    BjyVideoPlayManager.getMediaPlayer(this).bindPlayerView(mPlvVideo.getBjyPlayerView());
                }
                VideoHelper.resetRenderTypeTexture(mPlvVideo);
            }
        }
        count++;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void registerListener() {
        mLearnQrcodeCl.setOnClickListener(v -> mPresenter.showQrCodeDialog());
        mStatusView.setOnRetryClickListener(v -> mPresenter.getMyLearnedDetail());
        findViewById(R.id.tv_homework).setOnClickListener(v -> JumpHelper.jumpCourseHomeWork(mCourseId));
        mCommentCl.setOnClickListener(v -> mPresenter.comment());
        mCourseDetailCl.setOnClickListener(v -> mPresenter.goCourseDetail());
        mRemoveListCl.setOnClickListener(v -> mPresenter.hideOrRecoverCourse());
        rvAdapter.setOnItemClickListener((holder, position, view, item) -> {
            if (ClickUtils.isFastDoubleClick()) {
                return;
            }
            //只处理点击事件
            if (!(item instanceof SectionBean)) {
                return;
            }
            if (tryCheckClickItemReturnHint(true)) {
                return;
            }
            //点击下载的地方
            if (view.getId() == R.id.view_download_placeholder) {
                mPresenter.downloadSection(((SectionBean) item).getId());
                return;
            }
            //点击item
            checkAdapterItem(position, (SectionBean) item, true);
        });
    }

    public boolean tryCheckClickItemReturnHint(boolean needShowHint) {
        if (mPresenter.isCourseLimit()) {
            if (needShowHint) {
                ToastUtil.shortShow(this, R.string.course_limit);
            }
            return true;
        }
        if (mPresenter.isNotCanStudy()) {
            if (needShowHint) {
                new CourseUndercarriageDialog(getActivity()).show();
            }
            return true;
        }
        return false;
    }

    private void checkAdapterItem(int position, SectionBean item, boolean isFromClick) {
        //改变check的状态
        changeCheckIndex(position);
        if (tryCheckClickItemReturnHint(false)) {
            return;
        }
        //非点击状态过滤掉一些音视频课
        if (!isFromClick) {
            if (!(ConstsCouseType.isAudio(item.getCourseType()) || ConstsCouseType.isVideo(item.getCourseType()))) {
                return;
            }
        }
        //图文课
        if (ConstsCouseType.isImgTxt(item.getCourseType())) {
            CourseHelper.goImgTxtCourseAndCheckLimit(this,mCourseId,item.getId(), item.getPeriodsTitle(), String.valueOf(CourseHelper.getSystemCourseId(this)));
            return;
        }
        playItemWhenPassCheck(item);


    }

    private void playItemWhenPassCheck(SectionBean item) {
        if (CourseHelper.isCanPlay(item.getCourseType())) {
            setNeedUploadProgressItemId(item);
            //判断是否必须使用bjy sdk
            if (CourseHelper.isMustJumpBjySdk(item.getCourseType())) {
                mPresenter.playSectionByBjySdk(item.getId());
            } else {
                mPresenter.playSectionByCurrentPagePlayer(item.getId());
            }
        }
    }

    private void setNeedUploadProgressItemId(SectionBean item) {
        if (ConstsCouseType.isAudio(item.getCourseType()) || ConstsCouseType.isVideo(item.getCourseType())) {
            VideoUploadRecordManager.getInstance().saveItemInfo(String.valueOf(item.getId()), item.getProgressRate());
        }
    }

    private void updateSectionDownloadState(DownloadItem downloadItem) {
        for (int i = 0; i < rvAdapter.getItemCount(); i++) {
            if (rvAdapter.getItem(i) instanceof SectionBean &&
                    ((SectionBean) rvAdapter.getItem(i)).getId() == Integer.parseInt(downloadItem.getItemId())) {
                ((SectionBean) rvAdapter.getItem(i)).setDownloadState(downloadItem.getDownloadStatus());
                ((SectionBean) rvAdapter.getItem(i)).setDownloadProgress(downloadItem.getDownloadRate());
                rvAdapter.notifyItemChanged(i, MyLearnedCourseSectionHolder.PAY_LOAD_UPDATE_DOWNLOAD);
            }
        }
    }

    /**
     * 只应用于当前的函数
     */
    private void changeCheckIndex(int position) {
        //自己已经是被选中的,不需要改变选中的ui状态
        if (((SectionBean) rvAdapter.getItem(position)).isChecked()) {
            return;
        }

        //如果全部都是节:
        //需要吧上一个选中的去掉
        for (int i = 0; i < rvAdapter.getItemCount(); i++) {
            if (rvAdapter.getItem(i) instanceof SectionBean
                    && ((SectionBean) rvAdapter.getItem(i)).isChecked()) {
                //释放上一个选中的
                ((SectionBean) rvAdapter.getItem(i)).setChecked(false);
                //刷新ui
                rvAdapter.notifyItemChanged(i, MyLearnedCourseSectionHolder.PAY_LOAD_UPDATE_BG);
                break;
            }
        }
        //找出选中的那个设置为未选中
        Observable
                .just(mPresenter.getListData().get(0))
                .filter(o
                        -> o instanceof ChapterBean)
                .flatMap((Function<Object, ObservableSource<List<Object>>>) o
                        -> Observable.fromIterable(mPresenter.getListData()))
                .filter((Predicate<Object>) o
                        -> (o instanceof ChapterBean)
                        && ((ChapterBean) o).getChild() != null
                        && ((ChapterBean) o).getChild().size() > 0)
                .flatMap((Function<Object, ObservableSource<?>>) o
                        -> Observable.fromIterable(((ChapterBean) o).getChild()))
                .filter(o
                        -> ((SectionBean) o).isChecked())
                .takeWhile(o -> {
                    ((SectionBean) o).setChecked(false);
                    return false;
                }).subscribe();
        //选中自己
        ((SectionBean) rvAdapter.getItem(position)).setChecked(true);
        //刷新ui
        rvAdapter.notifyItemChanged(position, MyLearnedCourseSectionHolder.PAY_LOAD_UPDATE_BG);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        if (mPresenter.isNeedPlayer()) {
            mPlvVideo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mPlvVideo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mPlvVideo.setVisibility(View.VISIBLE);
                    mPresenter.getMyLearnedDetail();
                }
            });
        } else {
            mPresenter.getMyLearnedDetail();
        }
    }

    @Override
    public void setInfo(MyLearnedDetailWrapperBean myLearnedDetailWrapperBean) {
        setPageTitle(myLearnedDetailWrapperBean.getCourse().getTitle());
        setProgressUiData(myLearnedDetailWrapperBean);
        //列表相关
        setListUiData(myLearnedDetailWrapperBean);
        registerDownLoadListener(myLearnedDetailWrapperBean.getCourse().getCourseId());
        //选中上一次观看的
    }

    private void setProgressUiData(MyLearnedDetailWrapperBean myLearnedDetailWrapperBean) {
        mSectionsTv.setText(MessageFormat.format(getString(R.string.course_fmt_section), myLearnedDetailWrapperBean.getCourse().getSectionNum()));
        mLearnedTv.setText(MessageFormat.format(getString(R.string.course_format_learned), myLearnedDetailWrapperBean.getCourse().getProgressRate()));
        mPgLearned.setProgress(myLearnedDetailWrapperBean.getCourse().getProgressRate());
    }

    private void setListUiData(MyLearnedDetailWrapperBean myLearnedDetailWrapperBean) {
        if (CourseHelper.needAddOnlySectionTag(myLearnedDetailWrapperBean.getResult())) {
            rvAdapter.setTag(true);
            mRv.addItemDecoration(SpaceItemDecoration.create().setSpace(14));
        }
        rvAdapter.addAll(myLearnedDetailWrapperBean.getResult());
    }


    @SuppressLint("CheckResult")
    private void registerDownLoadListener(int parentId) {
        DownloadManager.getAllDownloadInfo(this, String.valueOf(parentId))
                .getAsFlow().subscribe((Consumer<List<DownloadItem>>) downloadItems -> {
            for (int i = 0; i < downloadItems.size(); i++) {
                if (downloadItems.get(i) == null) {
                    continue;
                }
                updateSectionDownloadState(downloadItems.get(i));
            }
        });
    }


    @Override
    public void playVideo(String token, String videoId) {
        if (mLastPlayVideoId.equals(videoId)) {
            return;
        }
        if (mPlvVideo.getPlayer() == null) {
            return;
        }
        mPlvVideo.getPlayer().setupOnlineVideoWithId(Long.parseLong(videoId), token);
        mLastPlayVideoId = videoId;
        mLastPlayToken=token;
        //保存上一次的播放课程id
    }

    @Override
    public void setCommentBtnText(String text) {
        ((TextView) findViewById(R.id.tv_comment)).setText(text);
    }

    @Override
    public void selectLastLearnPosition(int sectionId) {
        if (rvAdapter.getAllItems() == null || rvAdapter.getAllItems().size() <= 0) {
            return;
        }
        //查询出section chapter 第一个方section 第二个方chapter
        List<Object> objects = querySectionChapter(rvAdapter.getAllItems(), sectionId);
        if (objects == null || objects.size() <= 0) {
            //没有找到的话
            ExpandHelper.expandOrCollapseTree(rvAdapter, 0);
            return;
        }
        //是章节的话 先做展开初始化
        if (objects.size() == 2) {
            //设置展开属性
            ((ChapterBean) objects.get(1)).getTreeItemAttr().onExpand();
            //展开
            ExpandHelper.initExpand(rvAdapter);
        }
        //定位
        mRv.getLayoutManager().scrollToPosition(rvAdapter.getAllItems().indexOf(objects.get(0)));
        //选中

        checkAdapterItem(rvAdapter.getAllItems().indexOf(objects.get(0)), (SectionBean) objects.get(0), false);


    }

    @Override
    public void removeCourseSuccess() {
        //需要播放器的页面需要停止播放
//        if (mPresenter.isNeedPlayer()) {
//            BjyVideoPlayManager.releaseMedia();
//        }
//        finish();
    }

    @Override
    public void setQrCodeUi(String title) {
        mLearnQrcodeCl.setVisibility(View.VISIBLE);
        mLearnQrcodeTv.setText(title);

    }

    @Override
    public void setCourseHideStatus(boolean isHide) {
        ((TextView) findViewById(R.id.tv_hide)).setText(isHide?R.string.course_recover_hide_course:R.string.course_hide_course);
        ((ImageView) findViewById(R.id.iv_course_hide)).setImageResource(isHide?R.drawable.course_ic_learn_cancel_hide:R.drawable.course_ic_learn_set_hide);
    }

    private List<Object> querySectionChapter(List outLineResult, int sectionId) {
        List<Object> result = new ArrayList<>(2);
        //是yi级别列表的时候
        if (outLineResult.get(0) instanceof SectionBean) {
            for (Object obj : outLineResult) {
                if (sectionId == ((SectionBean) obj).getId()) {
                    result.add(obj);
                    return result;
                }
            }
            return result;
        }
        //是二级别列表的时候
        for (Object obj : outLineResult) {
            if (((ChapterBean) obj).getChild() == null) {
                continue;
            }
            for (SectionBean sectionBean : ((ChapterBean) obj).getChild()) {
                if (sectionId == sectionBean.getId()) {
                    result.add(sectionBean);
                    result.add(obj);
                    return result;
                }
            }

        }
        return result;

    }


    @Override
    public void onBackPressedSupport() {
        tryOpenFloatViewPlay(needReleasePlayer -> {
            if (needReleasePlayer) {
                BjyVideoPlayManager.releaseMedia();
            }
            finish();
        });
    }


    private void tryOpenFloatViewPlay(final ActionCallBack actionCallBack) {
        if (!needFloatWindow()) {
            actionCallBack.call(true);
            return;
        }
        if (PermissionUtils.checkPermission(this)) {
            openFloatPlayer(actionCallBack);
        } else {
            final CommonMDDialog commonmddialog = BJYDialogHelper.buildMDDialog(this);
            commonmddialog.setContentTxt("开启浮窗播放功能，需要您授权悬浮窗权限。")
                    .setNegativeTxt("暂不开启")
                    .setOnNegativeClickListener(() -> {
                        commonmddialog.dismiss();
                        actionCallBack.call(true);
                    })
                    .setPositiveTxt("去开启")
                    .setOnPositiveClickListener(() -> {
                        commonmddialog.dismiss();
                        openFloatPlayer(actionCallBack);
                    }).show();

        }
    }

    private void openFloatPlayer(ActionCallBack actionCallBack) {
        BjyVideoPlayManager.addFloatingView(getActivity(), () -> actionCallBack.call(false), MyLearnedCourseDetailActivity.class);
    }


    public interface ActionCallBack {
        void call(boolean needStopPlayer);
    }


    private boolean needFloatWindow() {
        return mPresenter.isNeedPlayer()
                && mPlvVideo.getPlayer() != null
                && mPlvVideo.getPlayer().isPlaying();
    }
}
