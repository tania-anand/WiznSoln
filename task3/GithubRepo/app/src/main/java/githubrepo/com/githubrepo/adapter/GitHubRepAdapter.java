package githubrepo.com.githubrepo.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import githubrepo.com.githubrepo.R;
import githubrepo.com.githubrepo.bean.GitHubRepo;

/**
 * Created by tania on 15/4/18.
 */

public class GitHubRepAdapter extends RecyclerView.Adapter<GitHubRepAdapter.ViewHolder> {

    Context context;
    ArrayList<GitHubRepo> gitHubRepoArrayList;


    public GitHubRepAdapter(Context context , ArrayList<GitHubRepo> gitHubRepoArrayList){
        this.context = context;
        this.gitHubRepoArrayList=gitHubRepoArrayList;

    }


    @Override
    public GitHubRepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.githubrep_item,parent,false);
        GitHubRepAdapter.ViewHolder viewHolder = new GitHubRepAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GitHubRepAdapter.ViewHolder holder, final int position) {

        holder.fullName.setText(gitHubRepoArrayList.get(position).getFullName());
        holder.countStar.setText(gitHubRepoArrayList.get(position).getStarCount());
        holder.countFork.setText(gitHubRepoArrayList.get(position).getForkCount());



        holder.viewRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(gitHubRepoArrayList.get(position).getUrl()));
                context.startActivity(browserIntent);



            }
        });



    }

    @Override
    public int getItemCount() {
        return gitHubRepoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullName,countStar,countFork;
        Button viewRepo;
        public ViewHolder(View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.repoFullName);
            countStar = itemView.findViewById(R.id.repoStar);
            countFork = itemView.findViewById(R.id.repoFork);
            viewRepo = itemView.findViewById(R.id.viewRepo);
        }
    }
}
