import {initializeApp} from 'firebase/app'

const firebaseConfig = {
    apiKey: "AIzaSyBbheAlczxoKxHUsbwoC5K0lS0xoT-i-qA",
    authDomain: "grenes-1759f.firebaseapp.com",
    projectId: "grenes-1759f",
    storageBucket: "grenes-1759f.appspot.com",
    messagingSenderId: "192957383102",
    appId: "1:192957383102:web:bc0e4f07f8e9dd054679ef",
    measurementId: "G-B1THCZXT4Q"
};

export const firebaseApp = initializeApp(firebaseConfig)