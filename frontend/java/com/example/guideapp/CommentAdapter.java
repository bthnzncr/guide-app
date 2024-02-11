    package com.example.guideapp;

    import android.content.Context;

    import android.view.LayoutInflater;

    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.RatingBar;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.constraintlayout.widget.ConstraintLayout;

    import androidx.recyclerview.widget.RecyclerView;

    import java.util.List;

    public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

        Context ctx;
        List<Review> data;

        public CommentAdapter(Context ctx, List<Review> data) {
            this.ctx = ctx;
            this.data = data;
        }

        @NonNull
        @Override
        public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(ctx).inflate(R.layout.comment_item, parent, false);
            return new CommentViewHolder(root);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
            Review comment = data.get(position);
            holder.authorTextView.setText(comment.getNickname());
            holder.commentTextView.setText(comment.getText());
            holder.ratingBar.setRating(comment.getRating());


        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        static class CommentViewHolder extends RecyclerView.ViewHolder {
            ConstraintLayout row;
            TextView authorTextView;
            TextView commentTextView;

            RatingBar ratingBar;

            public CommentViewHolder(@NonNull View itemView) {
                super(itemView);

                row = itemView.findViewById(R.id.comment_row);
                authorTextView = itemView.findViewById(R.id.commentAuthor);
                commentTextView = itemView.findViewById(R.id.commentText);
                ratingBar = itemView.findViewById(R.id.ratingBar);
            }
        }
    }
