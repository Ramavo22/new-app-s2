const hamburger = document.getElementById('customHamburger');
const sidebar = new bootstrap.Offcanvas(document.getElementById('sidebar'));

hamburger.addEventListener('click', () => {
    sidebar.show();
});