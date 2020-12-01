$(document).ready(function () {


    $("#login-btn").click(function () {

        var formData = getLoginForm();
        if (!validateLoginForm(formData)) {
            return;
        }

        postRequest(
            '/api/user/login',
            formData,
            function (res) {
                if (res.success) {
                    sessionStorage.setItem('username', formData.username);
                    //sessionStorage.setItem('id', res.content.id);
                    window.location.href = "/searchPage";
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                console.log("a");
                alert(error);
            });
    });

    function getLoginForm() {
        return {
            username: $('#index-name').val(),
            password: $('#index-password').val()
        };
    }

    function validateLoginForm(data) {
        var isValidate = true;
        if (!data.username) {
            isValidate = false;
            //$('#index-name').parent('.input-group').addClass('has-error');
            $('#index-name-error').css("display", "block");
        }
        if (!data.password) {
            isValidate = false;
            //$('#index-password').parent('.input-group').addClass('has-error');
            $('#index-password-error').css("display", "block");
        }
        console.log(isValidate);
        return isValidate;
    }
});

