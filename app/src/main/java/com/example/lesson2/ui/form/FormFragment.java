package com.example.lesson2.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lesson2.App;
import com.example.lesson2.R;
import com.example.lesson2.data.models.Post;
import com.example.lesson2.databinding.FragmentFormBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {

    private FragmentFormBinding binding;
    private Post post;

    public FormFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(getLayoutInflater(), container, false);
        binding.btnCreatePost.setVisibility(View.VISIBLE);
        binding.btnUpdatePost.setVisibility(View.GONE);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePost();
            }
        });
        updatePost();
    }

    private void updatePost() {
        post = (Post) requireArguments().getSerializable("post");
        if (post != null) {
            binding.btnCreatePost.setVisibility(View.GONE);
            binding.btnUpdatePost.setVisibility(View.VISIBLE);

            binding.etUserId.setText(String.valueOf(post.getUser()));
            binding.etTitle.setText(post.getTitle());
            binding.etDescription.setText(post.getContent());

            binding.btnUpdatePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.api.updatePost(post.getId(), getPost()).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            close();
                        }
                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });
                }

                private Post getPost() {
                    return new Post(
                            binding.etTitle.getText().toString(),
                            binding.etDescription.getText().toString(),
                            Integer.parseInt(binding.etUserId.getText().toString()), 35);
                }
            });

        }
    }

    private void savePost() {
        if (post == null) {
            post = new Post(
                    binding.etTitle.getText().toString(),
                    binding.etDescription.getText().toString(),
                    Integer.parseInt(binding.etUserId.getText().toString()),
                    35);
            App.api.createPost(post).enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    if (response.isSuccessful()) {
                        close();
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    Log.e("Naima2", "onFailure" + t.getLocalizedMessage());
                }
            });
        }
        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.postsFragment);
    }
}