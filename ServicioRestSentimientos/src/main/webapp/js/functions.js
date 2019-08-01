// When the user scrolls the page, execute myFunction
window.onscroll = function() {
	// Get the navbar
	var nav = document.getElementById("nav");
	var header = document.getElementById("header");

	// Get the offset position of the navbar
	var stickyHeight = header.offsetHeight;

	if (window.pageYOffset > stickyHeight) {
		nav.classList.add("sticky");
	} else {
		nav.classList.remove("sticky");
	}
};