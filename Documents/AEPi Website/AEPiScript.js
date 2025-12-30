//Slide Show
let slideIndex = 0;

if (document.getElementsByClassName("mySlides").length > 0) {
  showSlides();
}

function showSlides() {
  let i;
  let slides = document.getElementsByClassName("mySlides");
  let dots = document.getElementsByClassName("dot");

  if (slides.length === 0) return;

  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }

  slideIndex++;
  if (slideIndex > slides.length) { slideIndex = 1 }

  for (i = 0; i < dots.length; i++) {
    dots[i].className = dots[i].className.replace(" active", "");
  }

  slides[slideIndex - 1].style.display = "block";
  if (dots.length > 0) {
    dots[slideIndex - 1].className += " active";
  }

  setTimeout(showSlides, 10000);
}

function currentSlide(n) {
  slideIndex = n - 1;
  showSlides();
}

// Mobile menu toggle
const hamburger = document.querySelector('.hamburger');
const navMenu = document.querySelector('.nav-menu');

if (hamburger && navMenu) {
  hamburger.addEventListener('click', function() {
    hamburger.classList.toggle('active');
    navMenu.classList.toggle('active');
  });

  // Close menu when clicking on a link
  document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', function() {
      hamburger.classList.remove('active');
      navMenu.classList.remove('active');
    });
  });
}

// Handle subscription form submission
const subscriptionForm = document.getElementById('subscription-form');

function showMessage(message, type) {
  const existingMessage = document.querySelector('.form-message');
  if (existingMessage) {
    existingMessage.remove();
  }

  const messageDiv = document.createElement('div');
  messageDiv.className = `form-message form-message-${type}`;
  messageDiv.textContent = message;
  
  const subscriptionBox = document.querySelector('.subscription-box');
  subscriptionBox.insertBefore(messageDiv, subscriptionBox.querySelector('.subscription-form'));
  
  setTimeout(() => {
    messageDiv.classList.add('show');
  }, 10);
  
  setTimeout(() => {
    messageDiv.classList.remove('show');
    setTimeout(() => {
      messageDiv.remove();
    }, 300);
  }, 5000);
}

if (subscriptionForm) {
  subscriptionForm.addEventListener('submit', function (e) {
    e.preventDefault();
    e.stopPropagation();
    
    const submitButton = this.querySelector('.subscribe-button');
    const originalText = submitButton.textContent;
    
    submitButton.disabled = true;
    submitButton.textContent = 'Subscribing...';
    
    const formData = {
      firstName: document.getElementById('firstName').value,
      lastName: document.getElementById('lastName').value,
      relation: document.getElementById('relation').value,
      email: document.getElementById('email').value
    };
    
    fetch('https://script.google.com/macros/s/AKfycbyovfEoq6MBEkoKv5eXQiEIBLyJNW_okTkBX9GV5ur10TGCAPDc_aRXbQ7wQ9srs20Q/exec', {
      method: 'POST',
      mode: 'no-cors',
      cache: 'no-cache',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData)
    })
    .then(() => {
      // Show success message using showMessage instead of alert
      showMessage(`Thank you for subscribing, ${formData.firstName}!`, 'success');
      
      subscriptionForm.reset();
      
      submitButton.disabled = false;
      submitButton.textContent = originalText;
    })
    .catch((error) => {
      console.error('Error:', error);
      // Show error message using showMessage instead of alert
      showMessage('There was an error submitting the form. Please try again.', 'error');
      
      submitButton.disabled = false;
      submitButton.textContent = originalText;
    });
    
    return false;
  });
}