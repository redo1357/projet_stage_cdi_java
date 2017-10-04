import { Component, OnInit } from '@angular/core';
import { ControlContainer, FormBuilder, FormGroup, FormControl, FormArray, Validators } from '@angular/forms';
import { NavController, NavParams, AlertController } from 'ionic-angular';

import { Address } from '../../models/address.model';

import { AddressService } from '../../services/address.service';

import { RunCreate2Page } from '../run-create2/run-create2';

@Component({
  selector: 'page-run-create1',
  templateUrl: 'run-create1.html',
})
export class RunCreate1Page implements OnInit {
  formRunCreate1: FormGroup;

  constructor(public navCtrl: NavController, public navParams: NavParams, private addressService: AddressService, private alertCtrl: AlertController, private fb: FormBuilder) {
  }

  ngOnInit() {
    let startAddress = this.fb.group({
      town: null,
      district: null,
      place: null
    });
    let endAddress = this.fb.group({
      town: null,
      district: null,
      place: null
    });
    let places = new FormArray([]);
    this.formRunCreate1 = this.fb.group({
      startAddress: startAddress,
      endAddress: endAddress,
      places: places
    });
  }

  onAddPlace() {
    (<FormArray>this.formRunCreate1.get('places')).push(this.fb.group({
      town: null,
      district: null,
      place: null
    }));
  }

  removePlace(index: number) {
    (<FormArray>this.formRunCreate1.get('places')).removeAt(index);
  }

  onGoToStep2() {
    let runValues = { addresses: [this.formRunCreate1.get('startAddress').value] };
    let places = (<FormArray>this.formRunCreate1.get('places')).value;
    if (places != null && places.length > 0) {
      for (let place of places) {
        runValues.addresses.push(place);
      }
    }
    runValues.addresses.push(this.formRunCreate1.get('endAddress').value);
    console.log(runValues);
    this.navCtrl.push(RunCreate2Page, runValues);
  }

}
