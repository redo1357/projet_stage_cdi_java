import { App } from 'ionic-angular';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Camera, CameraOptions } from '@ionic-native/camera';

import { Image } from '../../models/image.model';
import { User } from '../../models/user.model';

import { HomePage } from '../../pages/home/home';

import { AuthProvider } from '../../providers/auth';

import { ImgService } from '../../services/image.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'page-user-signup',
  templateUrl: 'user-signup.html',
})
export class UserSignupPage {

    userPhoto: string = '';
    userIdCard: string = '';
    userDrivingLicence: string = '';
    private photoToSend: Image;
    private idCardToSend: Image;
    private drivingLicenceToSend: Image;
    private userToSave: User;

    constructor(private appCtrl: App, private authProvider: AuthProvider, private camera: Camera, private imgService: ImgService, private userService: UserService) {
    }

    private getRandomName(): string {
        const char: string = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        var name: string = '';
        for(let i = 0; i < 10; i++) name += char.substr(Math.random() * 62,1);
        return name + ".jpeg";
    }

    private getPicture(source: number, location: string, form: NgForm) {
        var destination: Image;
        const option: CameraOptions = {
            destinationType: 0,
            encodingType: this.camera.EncodingType.JPEG,
            mediaType: this.camera.MediaType.PICTURE,
            quality: 100,
            sourceType: source
        }

        this.camera.getPicture(option).then((value) => {
            destination = {
                image: value,
                name: this.getRandomName(),
                type: 'image/jpeg'
            }
            if(location === 'photo') {
                this.photoToSend = destination;
                this.userPhoto = 'data:image/jpeg;base64,' + value;
            }
            if(location === 'idCard') {
                this.idCardToSend = destination;
                this.userIdCard = 'data:image/jpeg;base64,' + value;
            }
            if(location === 'drivingLicence') {
                this.drivingLicenceToSend = destination;
                this.userDrivingLicence = 'data:image/jpeg;base64,' + value;
            }
            form.controls[location].setValue('ok');
        });

    }

    signup(values: any) {
        this.authProvider.authUser.subscribe(jwt => {
            this.userToSave = {
                accountId: jwt.accountDTO.accountId,
                lastName: values.lastName,
                firstName: values.firstName,
                mail: values.mail,
                photo: this.photoToSend.name,
                idCard: this.idCardToSend.name,
                drivingLicence: this.drivingLicenceToSend.name
            }
            this.imgService.add(this.photoToSend).subscribe();
            this.imgService.add(this.idCardToSend).subscribe();
            this.imgService.add(this.drivingLicenceToSend).subscribe();
            this.userService.add(this.userToSave).subscribe();
            this.appCtrl.getRootNavs()[0].setRoot(HomePage);
        })
    }

    takePhoto(form: NgForm, location: string) {
        this.getPicture(1, location, form);
    }

    takePicture(form: NgForm, location: string) {
        this.getPicture(0, location, form);
    }

}