$(document).ready(function () {
   //
   // $("#upload").click(function(){
   //     $.ajax({
   //             url: '/upload',
   //             type: 'POST',
   //             cache: false,
   //             data: new FormData($('#uploadForm')[0]),
   //             processData: false,
   //             contentType: false
   //         }).done(function(res) {
   //              if (res.message==null) {
   //                  alert("上传成功");
   //              } else {
   //                  alert(res.message);
   //              }
   //         }).fail(function(res) {});
   // })
    // 获取弹窗
    var modal = document.getElementById('myModal');

// 打开弹窗的按钮对象
    var btn = document.getElementById("myBtn");

// 获取 <span> 元素，用于关闭弹窗 that closes the modal
    var span = document.getElementsByClassName("close")[0];

// 点击按钮打开弹窗
    btn.onclick = function() {
        modal.style.display = "block";
    }

// 点击 <span> (x), 关闭弹窗
    span.onclick = function() {
        modal.style.display = "none";
    }

// 在用户点击其他地方时，关闭弹窗
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }}

    $("#crawler").click(function(){
        var index = 1;
        var formData = getSpiderForm();
        var idcode= $('#thecode').val();
        if(idcode=='123456'){
            modal.style.display = "none";
            console.log($( '#postForm').serialize())
            $.ajax({
                url: '/crawler',
                type: 'POST',
                cache: false,
                processData: false,
                contentType: false,
                data: new FormData($('#postForm')[0]),
            }).done(function(res) {
                if (res.message==null) {
                    alert("爬取成功");
                } else {
                    alert(res.message);
                }
            }).fail(function(res) {});

        }else{
            alert("您的验证码不正确！")
        }
    })

    function getSpiderForm() {
        return {

            database:$('input:radio[name="database"]:checked').val(),
            conference:$('#conference').val(),
            beginYear:$('#beginYear').val(),
            endYear:$('#endYear').val()
        };
    }

    function validateSpiderForm(data) {
        var isValidate = true;

        if (!isNaN(beginYear)||!isNaN(endYear)) {
            isValidate = false;
            $('#error').css("display", "block");
        }
        return isValidate;
    }





    $("#search-btn").click(function () {
        var index = 1;
        var formData = getLoginForm();
        if (!validateLoginForm(formData)) {
            return;
        }

        window.location.href="/paperList?paperName="+formData.header+"&author="+formData.author+"&affiliation="+formData.affiliation+"&publicationTitle="+formData.meeting+"&keywords="+formData.keyword+"&beginYear="+formData.first_year+"&endYear="+formData.last_year+"&currentPage="+index;
        /*
        getRequest(
            //paperName,author,affiliation,publicationTitle,keywords,beginYear,endYear,currentPage
            '/api/paper/searchPaper?paperName='+formData.header+'&author='+formData.author+'&affiliation='+formData.affiliation+'&publicationTitle='+formData.meeting+'&keywords='+formData.keyword+'&beginYear='+formData.first_year+'&endYear='+formData.last_year+'&currentPage='+index,
            //formData,
            function (res) {
                if (res.success) {
                    window.location.href = "/searchPage";
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                console.log("a");
                alert(error);
            });*/
    });

    function getLoginForm() {
        return {
            header: $('#header').val(),
            author: $('#author').val(),
            affiliation:$('#affiliation').val(),
            meeting:$('#meeting').val(),
            keyword:$('#keyword').val(),
            first_year:$('#first_year').val(),
            last_year:$('#last_year').val()
        };
    }

    function validateLoginForm(data) {
        var isValidate = true;
        //console.log(isNaN(parseInt(first_year)));
        if (!isNaN(first_year)||!isNaN(last_year)) {
            isValidate = false;
            $('#error').css("display", "block");
        }

        //console.log(isValidate);
        return isValidate;
    }
});

