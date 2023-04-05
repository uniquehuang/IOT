const URL_HEAD = "http://localhost:8080/device/";
const URL_HEAD_DEVICE_DATA = "http://localhost:8080/deviceData/";

let outIndex = 1;

//加载数据
function onDeviceManageLoad() {
    outIndex = 1;
    const tableBuilder = new DeviceTableBuilder();
    $.ajax({
        url: URL_HEAD + "getDevices",
        type: "get",
        datatype: "json",
        success: function (result) {
            // console.log(result);
            // $("#u108_input").options.length = 0;
            document.getElementById("u108_input").options.length = 0;
            for (let i = 0; i < result.resultData.length; i++) {
                // console.log(result.resultData[i].deviceId);
                tableBuilder.addDeviceRow(
                    result.resultData[i].deviceId,
                    result.resultData[i].deviceId,
                    result.resultData[i].deviceName,
                    result.resultData[i].updateTime,
                    result.resultData[i].isOnline,
                    result.resultData[i].dataType
                );
                $("#u108_input").append(new Option(result.resultData[i].deviceName, result.resultData[i].deviceId))
            }
            tableBuilder.build();
        }
    })

}

//轮询
function polling() {
    $("#btnctw").attr("disabled", true);

    $.ajax({
        url: "http://localhost:8080/mqtt/polling",
        type: "get",
        datatype: "json",
        success: function (result) {
            setTimeout(function () {
                let s = "";
                for (let i = 0; i < result.resultData.length; i++) {
                    s += result.resultData[i] + "\n";
                }
                $("#textctw").val(s)
                console.log("轮询" + s)
                $.ajax(polling());
            }, 2000)
        }
    })

}

//修改
function onModifyDeviceClick(index) {
    // alert($("#txtName-" + index).val());
    const newName = $("#txtName-" + index).val();
    const isOnline = $("#selOnline-" + index).val();
    const datatype = $("#selDataType-" + index).val();
    $.ajax({
        url: URL_HEAD + "updateDeviceById",
        type: "post",
        datatype: "json",
        data: {
            "deviceId": index,
            "deviceName": newName,
            "isOnline": isOnline,
            "dataType": datatype
        },
        success: function (result) {
            alert(result.resultMsg);
            onDeviceManageLoad();
        }
    })
}

function getDataType(dataTxt) {
    if (dataTxt === "数值测量值型") {
        return 0
    } else if (dataTxt === "开关状态型") {
        return 1
    } else if (dataTxt === "地理位置定位型") {
        return 2
    } else if (dataTxt === "文本预警消息型") {
        return 3
    }
}

//增加设备
function onAddDeviceClick() {
    const name = $("#u125_input").val();
    if (name == null || name === "" || name === "设备名称") {
        alert("设备名称不能为空！")
        return;
    }

    const isOnline = $("#u130_input").val() === "是";
    const datatypeTxt = $("#u126_input").val();
    let datatype = getDataType(datatypeTxt);

    $.ajax({
        url: URL_HEAD + "insertDevice",
        type: "post",
        datatype: "json",
        data: {
            "deviceName": name,
            "isOnline": isOnline,
            "dataType": datatype
        },
        success: function (result) {
            alert("插入成功")
            onDeviceManageLoad();
            $("#u125_input").val("");
        }
    })
}

//删除设备
function onDeleteDeviceClick(index) {
    // alert(index);
    // alert($("#selDataType-" + index).val())
    $.ajax({
        url: URL_HEAD + "removeDeviceById",
        type: "post",
        data: {
            "deviceId": index
        },
        datatype: "json",
        success: function (result) {
            onDeviceManageLoad()
            alert(result.resultMsg);
        },
        fail: function (result) {
            alert("删除失败")
        }
    })
}

/**
 * 该类实现设备表格动态添加行
 */
function DeviceTableBuilder() {
    this.builder = new StringBuilder();
    this.interval = 25;

    $("#u111-data").remove();
    this.builder.append('<div id="u111-data">');
}

DeviceTableBuilder.prototype.addDeviceRow = function (index, id, name, timestamp, online, dataType) {
    var rIdId = "rId-" + index;
    var txtNameId = "txtName-" + index;
    var rLatestTimestampId = "rLatestTimestamp-" + index;
    var selOnlineId = "selOnline-" + index;
    var selDataTypeId = "selDataType-" + index;
    var btnModifyDeviceId = "btnModifyDevice-" + index;
    var btnDeleteDeviceId = "btnDeleteDevice-" + index;
    // var rowTop = this.interval * (index - 1);
    var rowTop = this.interval * (outIndex - 1);
    outIndex++;

    //新的设备信息行
    this.builder.append('<div class="preeval" style="width: 561px; height: 25px;">');
    //设备ID
    this.builder.append('<div class="ax_default box_1 u116" data-label="rId" ');
    this.builder.append('style="width: 44px; height: 25px; left: 0px; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit">');
    this.builder.append('<div class="u116_div" style="width: 44px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<div class="text u116_text" style="visibility: inherit">');
    this.builder.append('<p id="');
    this.builder.append(rIdId);
    this.builder.append('"><span style="text-decoration:none;">');
    this.builder.append(id);
    this.builder.append('</span></p>');
    this.builder.append('</div>');
    this.builder.append('</div>');
    //设备名称
    this.builder.append('<div class="ax_default text_field u112" data-label="txtName"');
    this.builder.append(' style="width: 100px; height: 25px; left: 44px; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit">');
    this.builder.append('<div class="u112_div" style="width: 100px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<input id="');
    this.builder.append(txtNameId);
    this.builder.append('" type="text" value="');
    this.builder.append(name);
    this.builder.append('" class="u112_input" autocomplete="off"/>');
    this.builder.append('</div>');
    //更新时间
    this.builder.append('<div class="ax_default box_1 u117" data-label="rLatestTimestamp" ');
    this.builder.append('style="width: 155px; height: 25px; left: 144px; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit">');
    this.builder.append('<div class="u117_div" style="width: 155px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<div class="text u117_text" style="visibility: inherit">');
    this.builder.append('<p id="');
    this.builder.append(rLatestTimestampId);
    this.builder.append('"><span style="text-decoration:none;">');
    this.builder.append(timestamp);
    this.builder.append('</span></p>');
    this.builder.append('</div>');
    this.builder.append('</div>');
    //是否在线
    this.builder.append('<div class="ax_default droplist u118" data-label="selOnline" ');
    this.builder.append('style="width: 67px; height: 25px; left: 299px; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit">');
    this.builder.append('<div class="u118_div" style="width: 67px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<select id="');
    this.builder.append(selOnlineId);
    this.builder.append('" class="u118_input">');
    this.builder.append('<option class="u118_input_option" ');
    if (online === false) {
        this.builder.append('selected ');
    }
    this.builder.append('value="false">否</option>');
    this.builder.append('<option class="u118_input_option" ');
    if (online === true) {
        this.builder.append('selected ');
    }
    this.builder.append('value="true">是</option>');
    this.builder.append('</select>');
    this.builder.append('</div>');
    //数值类型
    this.builder.append('<div class="ax_default droplist u113" data-label="selDeviceType" ');
    this.builder.append('style="width: 115px; height: 25px; left: 366px; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit">');
    this.builder.append('<div class="u113_div" style="width: 115px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<select id="');
    this.builder.append(selDataTypeId);
    this.builder.append('" class="u113_input">');
    this.builder.append('<option class="u113_input_option" ');
    if (dataType === 0) {
        this.builder.append('selected ');
    }
    this.builder.append('value="0">数值测量值型</option>');
    this.builder.append('<option class="u113_input_option" ');
    if (dataType === 1) {
        this.builder.append('selected ');
    }
    this.builder.append('value="1">开关状态型</option>');
    this.builder.append('<option class="u113_input_option" ');
    if (dataType === 2) {
        this.builder.append('selected ');
    }
    this.builder.append('value="2">地理位置定位型</option>');
    this.builder.append('<option class="u113_input_option" ');
    if (dataType === 3) {
        this.builder.append('selected ');
    }
    this.builder.append('value="3">文本预警消息型</option>"');
    this.builder.append('</select>');
    this.builder.append('</div>');
    //修改按钮
    this.builder.append('<div id="');
    this.builder.append(btnModifyDeviceId);
    this.builder.append('" class="ax_default button u114" data-label="btnModifyDevice" ');
    this.builder.append('style="width: 40px; height: 25px; left: 481px; cursor: pointer; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit" onclick="onModifyDeviceClick(');
    this.builder.append(index);
    this.builder.append(')">');
    this.builder.append('<div class="u114_div" style="width: 40px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<div class="text u114_text" style="visibility: inherit">');
    this.builder.append('<p><span style="text-decoration:none;">修改</span></p>');
    this.builder.append('</div>');
    this.builder.append('</div>');
    //删除按钮
    this.builder.append('<div id="');
    this.builder.append(btnDeleteDeviceId);
    this.builder.append('" class="ax_default button u115" data-label="btnDeleteDevice" ');
    this.builder.append('style="width: 40px; height: 25px; left: 521px; cursor: pointer; top: ');
    this.builder.append(rowTop);
    this.builder.append('px;visibility: inherit" onclick="onDeleteDeviceClick(');
    this.builder.append(index);
    this.builder.append(')">');
    this.builder.append('<div class="u115_div" style="width: 40px; height: 25px;visibility: inherit"></div>');
    this.builder.append('<div class="text u115_text" style="visibility: inherit">');
    this.builder.append('<p><span style="text-decoration:none;">删除</span></p>');
    this.builder.append('</div>');
    this.builder.append('</div>');
    //设备行收尾
    this.builder.append('</div>');
}

DeviceTableBuilder.prototype.build = function () {
    this.builder.append('</div>');
    var value = this.builder.toString();
    //console.log(value);
    $("#u111").append(value);
}


//---------------------------------



function onDeviceDataLoad() {
    $.ajax({
        url: URL_HEAD + "getDevices",
        type: "get",
        datatype: "json",
        success: function (result) {
            // console.log(result);
            for (let i = 0; i < result.resultData.length; i++) {
                $("#u143_input").append(new Option(result.resultData[i].deviceName, result.resultData[i].deviceId))
            }
        }
    })
}

function addData() {
    let deviceId = $("#u143_input").val();
    let data = $("#u147_input").val();
    if (deviceId == null || deviceId < 0) {
        alert("请选择正确的设备");
        return;
    }
    if (data == null || data === "") {
        alert("请输入数值");
        return;
    }
    console.log("设备id = " + deviceId + "，数据 = " + data)

    $.ajax({
        url: URL_HEAD_DEVICE_DATA + "insertDeviceData",
        type: "post",
        data: {
            "deviceId": deviceId,
            "data": data
        },
        datatype: "json",
        success: function (result) {
            $("#u147_input").val("");
            alert(result.resultMsg);
        }
    })
}

function loadDataByDevice() {
    let deviceId = $("#u108_input").val();
    if (deviceId == null || deviceId < 0) {
        alert("请选择正确的设备");
        return;
    }
    $.ajax({
        url: URL_HEAD_DEVICE_DATA + "getDeviceDataById",
        type: "get",
        data: {
            "deviceId": deviceId
        },
        datatype: "json",
        success: function (result) {
            // alert(result.resultMsg);
            // console.log(result)
            let ss = "";
            for (let i = 0; i < result.resultData.length; i++) {
                let s = result.resultData[i].data;
                let d = result.resultData[i].createTime;
                ss += "时间：" + d + "，数据：" + s + "\n";
            }
            $("#textctw2").val(ss)
            // console.log(ss)
        }
    })
}