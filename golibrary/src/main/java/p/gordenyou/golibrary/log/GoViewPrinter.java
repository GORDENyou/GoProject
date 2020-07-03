package p.gordenyou.golibrary.log;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import p.gordenyou.golibrary.R;

/**
 * 用于日志可视化
 */
public class GoViewPrinter implements GoLogPrinter {

    private RecyclerView recyclerView;
    private LogAdapter logAdapter;
    private GoViewPrinterProvider printerProvider;

    public GoViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        recyclerView = new RecyclerView(activity);
        logAdapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(logAdapter);
        printerProvider = new GoViewPrinterProvider(rootView, recyclerView);
    }

    public GoViewPrinterProvider getPrinterProvider() {
        return printerProvider;
    }

    @Override
    public void print(@NotNull GoLogConfig config, int level, String tag, @NotNull String printString) {
        logAdapter.addItem(new GoLogMo(System.currentTimeMillis(), level, tag, printString));
        recyclerView.smoothScrollToPosition(logAdapter.getItemCount() - 1);
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {

        private LayoutInflater layoutInflater;
        private List<GoLogMo> logs = new ArrayList<>();

        public LogAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        void addItem(GoLogMo logItem){
            logs.add(logItem);
            notifyItemChanged(logs.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.golog_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            GoLogMo logItem = logs.get(position);
            int color = getHighlightColor(logItem.level);
            holder.tagView.setTextColor(color);
            holder.messageView.setTextColor(color);

            holder.tagView.setText(logItem.getFlattened());
            holder.messageView.setText(logItem.log);
        }

        /**
         * 根据log级别获取不同的高亮颜色
         *
         * @param logLevel log 级别
         * @return 高亮的颜色
         */
        private int getHighlightColor(int logLevel) {
            int highlight;
            switch (logLevel) {
                case GoLogType.V:
                    highlight = 0xffbbbbbb;
                    break;
                case GoLogType.D:
                    highlight = 0xffffffff;
                    break;
                case GoLogType.I:
                    highlight = 0xff6a8759;
                    break;
                case GoLogType.W:
                    highlight = 0xffbbb529;
                    break;
                case GoLogType.E:
                    highlight = 0xffff6b68;
                    break;
                default:
                    highlight = 0xffffff00;
                    break;
            }
            return highlight;
        }

        @Override
        public int getItemCount() {
            return logs.size();
        }
    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView tagView;
        TextView messageView;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tagView = itemView.findViewById(R.id.tag);
            messageView = itemView.findViewById(R.id.message);
        }
    }
}
