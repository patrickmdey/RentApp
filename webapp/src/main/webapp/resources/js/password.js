const togglePassword = $('form i.bi');

togglePassword.click((e) => {
   console.log('click');
   e.preventDefault();
   const target = $(e.target);
   const password = target.parents('.input-group').children('input'); // Search for input in same group should be password
   const type = password.prop('type') === 'password' ? 'text' : 'password';
   password.prop('type', type);
   // toggle the eye / eye slash icon
   target.toggleClass('bi-eye');
});