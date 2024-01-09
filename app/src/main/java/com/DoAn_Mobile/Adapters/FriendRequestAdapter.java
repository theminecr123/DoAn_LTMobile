package com.DoAn_Mobile.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DoAn_Mobile.Models.FriendRequest;
import com.DoAn_Mobile.R;

import java.util.List;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder> {
    private Context context;
    private List<FriendRequest> friendRequests;
    private OnFriendRequestListener listener;

    public interface OnFriendRequestListener {
        void onAcceptClicked(FriendRequest request);
        void onDeclineClicked(FriendRequest request);
    }

    public FriendRequestAdapter(Context context, List<FriendRequest> friendRequests, OnFriendRequestListener listener) {
        this.context = context;
        this.friendRequests = friendRequests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend_request, parent, false);
        return new FriendRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestViewHolder holder, int position) {
        FriendRequest request = friendRequests.get(position);
        holder.textViewUsername.setText(request.getFromUserId()); // Adjust this line if you have a username or other identifier
        holder.buttonAccept.setOnClickListener(v -> listener.onAcceptClicked(request));
        holder.buttonDecline.setOnClickListener(v -> listener.onDeclineClicked(request));
    }

    @Override
    public int getItemCount() {
        return friendRequests.size();
    }

    public class FriendRequestViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUsername;
        Button buttonAccept, buttonDecline;

        public FriendRequestViewHolder(View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.textView_friend_request_username);
            buttonAccept = itemView.findViewById(R.id.button_accept);
            buttonDecline = itemView.findViewById(R.id.button_decline);
        }
    }
}
