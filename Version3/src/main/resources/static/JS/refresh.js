
$(document).ready(refreshAttachedFactory());
function refreshAttachedFactory()
{
    $.ajax({
        type:'POST',
        url:'/changeScale/refresh/AttachedFactory',
        data:{},
        success:function (data) {
            for (let i=0;i<data.length;i++)
            {
                $("#select1").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                $("#select2").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
            }
        },
        error:function ()
        {
            console.log("请求附属工厂数据失败\n");
        }
    })
}
function refreshBrotherWorkshop()
{
    let option = $("#select1 option:selected");
    let id = option[0].value;
    let haveNoWorkshop = 1;
    $("#select4").empty();
    $.ajax({
        type: 'POST',
        url:'/changeScale/refresh/BrotherWorkshop',
        data: {"factory_id":id},
        success:function (data) {
            for (let i=0;i<data.length;i++)
            {
                $("#select4").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                haveNoWorkshop = 0;
            }
            if (haveNoWorkshop==1)
            {
                $("#select4").append("<option>-</option>");
            }
        },
        error: function () {
            console.log("请求兄弟车间失败\n");
        }

    })
}
function refreshAttachedWorkshop()
{
    let option = $("#select2 option:selected");
    let id = option[0].value;
    let haveNoWorkshop = 1;
    $("#select3").empty();
    $.ajax({
        type: 'POST',
        url:'/changeScale/refresh/AttachedWorkshop',
        data: {"factory_id":id},
        success:function (data) {
            for (let i=0;i<data.length;i++)
            {
                $("#select3").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                haveNoWorkshop = 0;
            }
            if (haveNoWorkshop==1)
            {
                $("#select3").append("<option>-</option>");
            }
        },
        error: function () {
            console.log("请求附属车间失败\n");
        }

    })
}
function refreshBrotherFacility()
{
    let option = $("#select3 option:selected");
    let id = option[0].value;
    let haveNoFacility = 1;
    $("#select5").empty();
    $.ajax({
        type: 'POST',
        url:'/changeScale/refresh/BrotherFacility',
        data: {"workshop_id":id},
        success:function (data) {
            for (let i=0;i<data.length;i++)
            {
                $("#select5").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                haveNoFacility = 0;
            }
            if (haveNoFacility==1)
            {
                $("#select5").append("<option>-</option>");
            }
        },
        error: function () {
            console.log("请求兄弟设备失败\n");
        }

    })
}