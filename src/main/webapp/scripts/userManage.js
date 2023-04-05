const URL_HEAD = "http://localhost:8080/user/";

function onUserManageLoad() {
    var tableBuilder = new UserTableBuilder();
    tableBuilder.addUserRow(1, "1", "张三", "zhangsan@sina.cn", 12, 0, "123456");
    tableBuilder.addUserRow(2, "2", "李四", "lisi@163.com", 21, 1, "654321");
    tableBuilder.build();
}

function onModifyUserClick(index) {
    alert(index);
    alert("name: " + $("#txtUserName-" + index).val()
        + ", email: " + $("#txtEmail-" + index).val()
        + ", age: " + $("#txtAge-" + index).val()
        + ", role: " + $("#selRole-" + index).val()
        + ", password: " + $("#txtPassword-" + index).val());
}

function onDeleteUserClick(index) {
    alert(index);
    alert($("#selRole-" + index).val())
}

function UserTableBuilder() {
    this.builder = new StringBuilder();
    this.interval = 25;

    $("#u73-data").remove();
    this.builder.append('<div id="u73-data">');
}

UserTableBuilder.prototype.addUserRow = function (index, id, name, email, age, role, password) {
    //alert("index: " + index);
    var rUserIdId = "rUserId-" + index;
    var txtNameId = "txtUserName-" + index;
    var txtEmailId = "txtEmail-" + index;
    var txtAgeId = "txtAge-" + index;
    var selRoleId = "selRole-" + index;
    var txtPasswordId = "txtPassword-" + index;
    var btnModifyUserId = "btnModifyUser-" + index;
    var btnDeleteUserId = "btnDeleteUser-" + index;
    var rowTop = this.interval * (index - 1);

    //新的用户信息行
    this.builder.append('<div class="preeval" style="width: 646px; height: 25px;">');
    //用户ID
    this.builder.append('<div class="ax_default box_1 u81" data-label="rId" ');
    this.builder.append('style="width: 44px; height: 25px; left: 0px; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit">');
    this.builder.append('<div class="u81_div" style="width: 44px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<div class="text u81_text" style="visibility: inherit">');
    this.builder.append('<p id="');
    this.builder.append(rUserIdId);
    this.builder.append('"><span style="text-decoration:none;">');
    this.builder.append(id);
    this.builder.append('</span></p>');
    this.builder.append('</div>');
    this.builder.append('</div>');
    //用户名
    this.builder.append('<div class="ax_default text_field u74" data-label="txtName" ');
    this.builder.append('style="width: 100px; height: 25px; left: 44px; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit">');
    this.builder.append('<div class="u74_div" style="width: 100px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<input id="');
    this.builder.append(txtNameId);
    this.builder.append('" type="text" value="');
    this.builder.append(name);
    this.builder.append('" class="u74_input"/>');
    this.builder.append('</div>');
    //邮箱
    this.builder.append('<div class="ax_default text_field u75" data-label="txtEmail" ');
    this.builder.append('style="width: 155px; height: 25px; left: 144px; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit">');
    this.builder.append('<div class="u75_div" style="width: 155px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<input id="');
    this.builder.append(txtEmailId);
    this.builder.append('" type="text" value="');
    this.builder.append(email);
    this.builder.append('" class="u75_input"/>');
    this.builder.append('</div>');
    //年龄
    this.builder.append('<div class="ax_default text_field u76" data-label="txtAge" ');
    this.builder.append('style="width: 67px; height: 25px; left: 299px; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit">');
    this.builder.append('<div class="u76_div" style="width: 67px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<input id="');
    this.builder.append(txtAgeId);
    this.builder.append('" type="text" value="');
    this.builder.append(age);
    this.builder.append('" class="u76_input"/>');
    this.builder.append('</div>');
    //角色
    this.builder.append('<div class="ax_default droplist u77" data-label="selRole" ');
    this.builder.append('style="width: 100px; height: 25px; left: 366px; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit">');
    this.builder.append('<div class="u77_div" style="width: 100px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<select id="');
    this.builder.append(selRoleId);
    this.builder.append('" class="u77_input">');
    this.builder.append('<option class="u77_input_option" ');
    if (role === 0) {
        this.builder.append('selected ');
    }
    this.builder.append('value="0">普通用户</option>');
    this.builder.append('<option class="u77_input_option" ');
    if (role === 1) {
        this.builder.append('selected ');
    }
    this.builder.append('value="1">管理员</option>');
    this.builder.append('<option class="u77_input_option" ');
    if (role === 2) {
        this.builder.append('selected ');
    }
    this.builder.append('value="2">开发者</option>');
    this.builder.append('</select>');
    this.builder.append('</div>');
    //密码
    this.builder.append('<div class="ax_default text_field u78" data-label="txtPassword" ');
    this.builder.append('style="width: 100px; height: 25px; left: 466px; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit">');
    this.builder.append('<div class="u78_div" style="width: 100px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<input id="');
    this.builder.append(txtPasswordId);
    this.builder.append('" type="text" value="');
    this.builder.append(password);
    this.builder.append('" class="u78_input"/>');
    this.builder.append('</div>');
    //修改按钮
    this.builder.append('<div id="');
    this.builder.append(btnModifyUserId);
    this.builder.append('" class="ax_default button u79" data-label="btnModifyUser" ');
    this.builder.append('style="width: 40px; height: 25px; left: 566px; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit" onclick="onModifyUserClick(');
    this.builder.append(index);
    this.builder.append(')">');
    this.builder.append('<div class="u79_div" style="width: 40px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<div class="text u79_text" style="visibility: inherit">');
    this.builder.append('<p><span style="text-decoration:none;">修改</span></p>');
    this.builder.append('</div>');
    this.builder.append('</div>');
    //删除按钮
    this.builder.append('<div id="');
    this.builder.append(btnDeleteUserId);
    this.builder.append('" class="ax_default button u80" data-label="btnDeleteUser" ');
    this.builder.append('style="width: 40px; height: 25px; left: 606px; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit" onclick="onDeleteUserClick(');
    this.builder.append(index);
    this.builder.append(')">');
    this.builder.append('<div class="u80_div" style="width: 40px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<div class="text u80_text" style="visibility: inherit">');
    this.builder.append('<p><span style="text-decoration:none;">删除</span></p>');
    this.builder.append('</div>');
    this.builder.append('</div>');

    this.builder.append('</div>');
}

UserTableBuilder.prototype.build = function () {
    this.builder.append('</div>');
    var value = this.builder.toString();
    //console.log(value);
    $("#u73").append(value);
}
function onLoginClick() {
    const userName = $("#u10_input").val();
    if (!userName) {
        alert("用户名不得为空");
        return;
    }

    const password = $("#u11_input").val();
    if (!password) {
        alert("密码不得为空");
        return;
    }

    $.ajax({
        url: URL_HEAD + "login",
        //通过type来判断调用哪个方法
        type: "post",
        data: {
            "name": userName,
            "password": password
        },
        dataType: "json",
        success: function (result) {
            alert(result.msg);
            if (result.status === 1) {
                //跳转到注册页面
                window.location.href = "register.html";
            } else if (result.status === 3) {
                //登录成功，保存token
                window.localStorage.setItem(ITEM_KEY_TOKEN_VALUE, result.data);
                window.location.href = "userCenter.html";
            }
            //密码错误
        },
        error: function (xhr, textStatus, errorThrown) {
            showError("登录异常", xhr, textStatus, errorThrown);
        }
    });
}