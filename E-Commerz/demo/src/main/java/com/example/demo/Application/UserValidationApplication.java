package com.example.demo.Application;

import com.example.demo.Exceptions.AssetException;
import com.example.demo.Infrastructure.Controllers.DTOs.UserDTO;
import com.example.demo.Infrastructure.Ports.AssetConsumerValidation;
import com.example.demo.Infrastructure.Ports.AssetLifecycle;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserValidationApplication implements AssetConsumerValidation<UserDTO> {

    @Autowired
    private AssetLifecycle<UserDTO> assetLifecycle;

    public void assetCreateValidation(UserDTO asset){
        if(usernameValidation(asset) && passwordValidation(asset.getPassword().getPassword())){
            if(!usernameDuplicate(asset.getUsername().getUsername())){
                this.assetLifecycle.createAsset(asset);
            } else {
                throw new AssetException("Try another username.", AssetException.ErrorCode.USERNAME_DUPLICATE, HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new AssetException("Credentials Error", AssetException.ErrorCode.CREDENTIALSERROR, HttpStatus.BAD_REQUEST);
        }
    }
    public void assetUpdateValidation(String asset, UserDTO updater){
        if(usernameValidation(updater.getUsername().getUsername()) && passwordValidation(updater.getPassword().getPassword())){
            if(usernameDuplicate(asset)){
                if(!usernameDuplicate(asset,updater.getUsername().getUsername())){
                    this.assetLifecycle.updateAssetByAsset(asset, updater);
                }
                else {
                    throw new AssetException("Try another username.", AssetException.ErrorCode.USERNAME_DUPLICATE, HttpStatus.BAD_REQUEST);
                }
            }
            else {
                throw new AssetException("User not found.", AssetException.ErrorCode.USERNOTFOUND, HttpStatus.BAD_REQUEST);
            }
        }
        else {
            throw new AssetException("Credentials Error", AssetException.ErrorCode.CREDENTIALSERROR, HttpStatus.BAD_REQUEST);
        }
    }

    private boolean passwordValidation(String password) {
        String regExpn = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
    private boolean usernameValidation(UserDTO asset) {
        String regExpn = "^[A-Za-z0-9][A-Za-z0-9]{5,29}$";
        String username = asset.getUsername().getUsername();

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }
    private boolean usernameValidation(String name) {
        String regExpn = "^[A-Za-z0-9][A-Za-z0-9]{5,29}$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }

    private boolean usernameDuplicate(String name){
        boolean validator = false;
        if(name.matches("^[0-9]*$")){
            return this.assetLifecycle.getAssetById(Integer.parseInt(name)).isPresent();
        } else{
            return this.assetLifecycle.getAssetByName(name).isPresent();
        }
    }
    private boolean usernameDuplicate(String name, String updaterUsername){
        boolean validator = true;
        if(name.matches("^[0-9]*$")){
            if(this.assetLifecycle.getAssetById(Integer.parseInt(name)).isPresent()){
                if(this.assetLifecycle.getAssetById(Integer.parseInt(name)).get()
                        .getUsername().getUsername().equals(updaterUsername)){
                    validator = false;
                } else if (this.assetLifecycle.getAssetByName(updaterUsername).isEmpty()) {
                    validator = false;
                }
            }
        } else{
            if(this.assetLifecycle.getAssetByName(name).isPresent()){
                if(this.assetLifecycle.getAssetByName(name).get()
                        .getUsername().getUsername().equals(updaterUsername)){
                    validator = false;
                } else if (this.assetLifecycle.getAssetByName(updaterUsername).isEmpty()) {
                    validator = false;
                }
            }
            else {
                validator = false;
            }
        }
        return validator;
    }
}
