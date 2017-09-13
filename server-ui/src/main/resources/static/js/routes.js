function headerAlign(){
    var $table = $('table'),
        $bodyCells = $table.find('tbody tr:first').children(),
        colWidth;

    // Get the tbody columns width array
    colWidth = $bodyCells.map(function() {
        return $(this).width();
    }).get();

    // Set the width of thead columns
    $table.find('thead tr').children().each(function(i, v) {
        $(v).width(colWidth[i]);
    });
}

function createTrackerPoint(){
    $("#back_shadow").show();
    $("#entity_box").show();
    $("#boxType").val("create");
}

function editTrackerPoint(){
    $("#back_shadow").show();
    $("#entity_box").show();
    $("#entity_box #idInput").prop("readonly","readonly");
    $("#entity_box #timeInput").prop("readonly","readonly");
    $("#entity_box #idInput").val($(".selected .number").text());
    $("#entity_box #timeInput").val($(".selected .time").text());
    $("#entity_box #latInput").val($(".selected .lat").text());
    $("#entity_box #lonInput").val($(".selected .lon").text());
    $("#entity_box #speedInput").val($(".selected .speed").text());
    $("#entity_box #bearingInput").val($(".selected .bearing").text());
    $("#boxType").val("edit");
}

function getValuesFromBox(){

    var trackerId =  $("#entity_box #idInput").val();
    var time = $("#entity_box #timeInput").val();
    var lat = $("#entity_box #latInput").val();
    var lon = $("#entity_box #lonInput").val();
    var instSpeed = $("#entity_box #speedInput").val();
    var bearing = $("#entity_box #bearingInput").val();

    var toServer = '{"trackerId":"' + trackerId +
        '","time":' + time +
        ',"lat":' + lat +
        ',"lon":'+ lon +
        ',"instSpeed":'+ instSpeed +
        ',"bearing":'+ bearing +'}';

    return toServer;
}

function getLine(v){
    return '<tr id="' + v.time + '"><td class="first_element number">' + v.trackerId  + '</td>' +
        '<td class="time">' + v.time + '</td>' +
        '<td class="lat">' + v.lat + '</td>' +
        '<td class="lon">' + v.lon + '</td>' +
        '<td class="speed">' + v.instSpeed + '</td>' +
        '<td class="bearing">' + v.bearing + '</td></tr>';
}


function boxCleaner(){
    $("#entity_box").hide();
    $("#back_shadow").hide();
    $("#entity_box input[type='text']").val("");
    $("#entity_box input[type='text']").removeAttr("readonly");
    $("#boxType").val("");
}

function deleteTrackerPoint(trackerId, time){

    jQuery.support.cors = true;

    $.ajax ({
        type:'POST',
        url: 'http://localhost:8080/deletePoint',
        data: '{"trackerId":"' + trackerId + '","time":' + time + '}',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (result) {
            alert(result.message);
            $("#entityList .selected").remove();
            $("#delete").prop("disabled","disabled");
            $("#edit").prop("disabled","disabled");
            headerAlign();
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert($(jqXHR).html() + " " +  textStatus + ": " + errorThrown);
        }
    })

}


function createPoint(){

    jQuery.support.cors = true;

    var toServer = getValuesFromBox();

    $.ajax ({
        type:'POST',
        url: 'http://localhost:8080/createPoint',
        data: toServer,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (v) {
            alert("Запись для устройства с номером " + v.trackerId + " создана");
            var line = getLine(v);
            $("#entityList").prepend(line);
            $("tbody tr").removeClass("selected");
            $("#"+v.time).addClass("selected");
            $("#delete").removeAttr("disabled");
            $("#edit").removeAttr("disabled");
            boxCleaner();
            headerAlign();
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert($(jqXHR).html() + " " +  textStatus + ": " + errorThrown);
        }
    })

}

function editPoint(){

    jQuery.support.cors = true;

    var toServer = getValuesFromBox();

    $.ajax ({
        type:'POST',
        url: 'http://localhost:8080/editPoint',
        data: toServer,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (v) {
            alert("Запись для устройства с номером " + v.trackerId + " и временем " +
                v.time + " успешно изменена");
            $(".selected .lat").text(v.lat);
            $(".selected .lon").text(v.lon);
            $(".selected .speed").text(v.instSpeed);
            $(".selected .bearing").text(v.bearing);
            boxCleaner();
            headerAlign();
        },
        error: function(jqXHR, textStatus, errorThrown){
            alert($(jqXHR).html() + " " +  textStatus + ": " + errorThrown);
        }
    })

}

function rest(trackerId, quantity){

    jQuery.support.cors = true;

    $.ajax(
        {
            type: 'POST',
            url: 'http://localhost:8080/track',
            data: '{"trackerId":"' + trackerId + '","pointCount":' + quantity + '}',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (result) {

                var trHTML = '';

                $.each(result, function(k, v){

                    trHTML += '<tr id="' + k + '"><td class="first_element number">' + v.trackerId  + '</td>' +
                        '<td class="time">' + v.time + '</td>' +
                        '<td class="lat">' + v.lat + '</td>' +
                        '<td class="lon">' + v.lon + '</td>' +
                        '<td class="speed">' + v.instSpeed + '</td>' +
                        '<td class="bearing">' + v.bearing + '</td></tr>';
                });

                $('#entityList tbody').html(trHTML);
                headerAlign();

            },

            error: function(jqXHR, textStatus, errorThrown){
                alert($(jqXHR).html() + " " +  textStatus + ": " + errorThrown);
            }
        });
}

$(document).ready(function(){

    rest( $("#trackerIdInput").val(), $("#pointCountInput").val());

    $("#getPoints").on("click",function(){
        rest( $("#trackerIdInput").val(), $("#pointCountInput").val());
    })

    $("tbody").on("click", "tr", function(){
        if ($(this).hasClass("selected")){
            $(this).removeClass("selected");
            $("#delete").prop("disabled","disabled");
            $("#edit").prop("disabled","disabled");
        }
        else{
            $("tbody tr").removeClass("selected");
            $(this).addClass("selected");
            $("#delete").removeAttr("disabled");
            $("#edit").removeAttr("disabled");
        }
    })

    $("#delete").on("click", function(){
        var trackerId = $(".selected .number").text();
        var time = $(".selected .time").text();
        deleteTrackerPoint(trackerId, time);
    })

    $("#edit").on("click", function(){
        editTrackerPoint();
    })

    $("#create").on("click", function(){
        createTrackerPoint();
    })

    $("#cancel").on("click", function(){
        boxCleaner();
    })

    $("#ok").on("click", function(){
        if ($("#boxType").val() == "create"){
            createPoint();
        }
        else if ($("#boxType").val() == "edit"){
            editPoint();
        }
    })

})