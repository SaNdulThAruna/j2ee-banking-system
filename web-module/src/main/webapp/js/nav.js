import {setupToggle} from "./util.js";

const hamburgerMenu = document.querySelector('.hamburger');
const sideNav = document.querySelector('.mobile-nav');
const closeButton = document.querySelector('.close');

setupToggle(hamburgerMenu, sideNav);
setupToggle(closeButton, sideNav);