package com.poly.appmessage.ui.userlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.poly.appmessage.data.User;
import com.poly.appmessage.data.UserRepository;

import java.util.Set;

public class UserListViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    UserRepository mUserRepository;
    LiveData<Set<User>> mAllUser;

    public UserListViewModel(){
        mUserRepository = new UserRepository();
        mAllUser = mUserRepository.getAllUsers();
    }

    public LiveData<Set<User>> getmAllUser(){
        return mAllUser;
    }
}