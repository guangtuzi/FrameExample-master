package com.otcyan.jwidget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tt.whorlviewlibrary.WhorlView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.otcyan.jwidget.LoadMoreRecyclerView.LoadType.AUTO_LOAD;
import static com.otcyan.jwidget.LoadMoreRecyclerView.LoadType.MANUAL_LOAD;

/**
 * 上拉加载.
 */

public class LoadMoreRecyclerView extends RecyclerView {

    @IntDef({AUTO_LOAD , MANUAL_LOAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadType{
        int AUTO_LOAD = 0;
        int MANUAL_LOAD =1;
    }

    public static enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    /**
     * item 类型
     */
    public final static int TYPE_FOOTER = 2;//底部--往往是loading_more
    public final static int TYPE_LIST = 3;//代表item展示的模式是list模式
    private boolean mIsFooterEnable;//是否允许加载更多
    private boolean usePicture = false;//是否使用图片动画作为进度条
    int loadType = AUTO_LOAD;
    /**
     * layoutManager的类型（枚举）
     */
    protected LAYOUT_MANAGER_TYPE layoutManagerType;
    /**
     * 自定义实现了头部和底部加载更多的adapter
     */
    private AutoLoadAdapter mAutoLoadAdapter;
    /**
     * 标记是否正在加载更多，防止再次调用加载更多接口
     */
    private boolean mIsLoadingMore;
    /**
     * 标记加载更多的position
     */
    private int mLoadMorePosition;
    /**
     * 加载更多的监听-业务需要实现加载数据
     */
    private LoadMoreListener mListener;
    private TextView loadmore;
    private WhorlView whorlView;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    public LoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化-添加滚动监听
     * <p>
     * 回调加载更多方法，前提是
     * <pre>
     *    1、有监听并且支持加载更多：null != mListener && mIsFooterEnable
     *    2、目前没有在加载，正在上拉（dy>0），当前最后一条可见的view是否是当前数据列表的最后一条--及加载更多
     * </pre>
     */
    private void init() {
        super.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (null != mListener && mIsFooterEnable && !mIsLoadingMore && dy >= 0) {
                    int lastVisiblePosition = getLastVisiblePosition();
                    if (lastVisiblePosition + 1 == mAutoLoadAdapter.getItemCount() && loadType == AUTO_LOAD) {
                        setLoadingMore(true);
                        mLoadMorePosition = lastVisiblePosition;
                        mListener.onLoadMore();
                    }
                }
            }
        });
    }

    /**
     * 设置加载更多的监听
     */
    public void setLoadMoreListener(LoadMoreListener listener) {
        mListener = listener;
    }

    /**
     * 设置正在加载更多
     */
    public void setLoadingMore(boolean loadingMore) {
        this.mIsLoadingMore = loadingMore;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter != null) {
            mAutoLoadAdapter = new AutoLoadAdapter(adapter);
            adapter.registerAdapterDataObserver(new AdapterDataObserver() {
                @Override
                public void onChanged() {
                    mAutoLoadAdapter.notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    mAutoLoadAdapter.notifyItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                    mAutoLoadAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    mAutoLoadAdapter.notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    mAutoLoadAdapter.notifyItemRangeRemoved(positionStart, itemCount);
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    mAutoLoadAdapter.notifyItemRangeChanged(fromPosition, toPosition, itemCount);
                }
            });
        }
        super.swapAdapter(mAutoLoadAdapter, true);
    }

    /**
     * 获取最后一条展示的位置
     */
    private int getLastVisiblePosition() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LINEAR:
                return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager
                        = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                return findMax(lastPositions);
        }
        return ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();

    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 设置将加载方式，是自动加载还是手动加载
     */
    public void setLoadType(int loadType) {
        this.loadType = loadType;
    }

    /**
     * 设置是否支持自动加载更多
     */
    public void setAutoLoadMoreEnable(boolean autoLoadMore) {
        mIsFooterEnable = autoLoadMore;
    }

    /**
     * 通知更多的数据已经加载
     * <p>
     * 每次加载完成之后添加了Data数据，用notifyItemRemoved来刷新列表展示，
     * 而不是用notifyDataSetChanged来刷新列表
     */
    public void notifyMoreFinish(boolean hasMore) {
        setAutoLoadMoreEnable(hasMore);
        if (loadType == AUTO_LOAD) {
            getAdapter().notifyItemRemoved(mLoadMorePosition);
        } else {
            getAdapter().notifyItemChanged(mLoadMorePosition + 1);
            //getAdapter().notifyDataSetChanged();
        }
        mIsLoadingMore = false;
    }

    /**
     * 加载更多监听
     */
    public interface LoadMoreListener {
        /**
         * 加载更多
         */
        void onLoadMore();
    }

    /**
     * 自动加载的适配器
     */
    public class AutoLoadAdapter extends Adapter<ViewHolder> {
        /**
         * 数据adapter
         */
        private Adapter mInternalAdapter;

        public AutoLoadAdapter(Adapter adapter) {
            mInternalAdapter = adapter;
        }

        @Override
        public int getItemViewType(int position) {
            int footerPosition = getItemCount() - 1;
            if (footerPosition == position && mIsFooterEnable) {
                return TYPE_FOOTER;
            } else {
                return TYPE_LIST;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_FOOTER) {
                if (usePicture) {
                    return new FooterViewHolder(
                            LayoutInflater.from(parent.getContext()).inflate(
                                    R.layout.list_foot_use_pic, parent, false));
                } else if (loadType == AUTO_LOAD) {
                    return new FooterViewHolder(
                            LayoutInflater.from(parent.getContext()).inflate(
                                    R.layout.list_foot_loading, parent, false));
                } else {
                    return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.list_foot_loadmore, parent, false));
                }
            } else {
                return mInternalAdapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int type = getItemViewType(position);
            if (type != TYPE_FOOTER) {
                mInternalAdapter.onBindViewHolder(holder, position);
            }
        }

        /**
         * 需要计算上加载更多
         */
        @Override
        public int getItemCount() {
            int count = mInternalAdapter.getItemCount();
            if (mIsFooterEnable) count++;
            return count;
        }

        public class FooterViewHolder extends ViewHolder {
            public FooterViewHolder(View itemView) {
                super(itemView);
                if (usePicture) {
                    //使用图片动画
                    ImageView image = (ImageView) itemView.findViewById(R.id.image);
                    image.setBackgroundResource(R.drawable.anim_list);
                    AnimationDrawable animationDrawable = (AnimationDrawable) image.getBackground();
                    animationDrawable.start();
                } else {
                    //不是用图片动画
                    if (loadType == AUTO_LOAD) {
                        WhorlView whorlView = (WhorlView) itemView.findViewById(R.id.whorl);
                        whorlView.start();
                    } else {
                        loadmore = (TextView) itemView.findViewById(R.id.loadmore);
                        whorlView = (WhorlView) itemView.findViewById(R.id.whorl);
                        whorlView.start();
                        loadmore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!mIsLoadingMore) {
                                    setLoadingMore(true);
                                    whorlView.setVisibility(VISIBLE);
                                    loadmore.setVisibility(GONE);
                                    mListener.onLoadMore();
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    /**
     * 处理回调，使loadmore显示和whorlView隐藏
     */
    public void handleCallback() {
        whorlView.setVisibility(GONE);
        loadmore.setVisibility(VISIBLE);
    }

    public void setUsePicture(boolean usePicture) {
        this.usePicture = usePicture;
    }
}