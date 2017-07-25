<html>
<head>
<title>CEVO Demo</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="">

<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>

<style>
h1 {
	font-size: 1em;
	font-weight: bold;
}
</style>
</head>

<body style='margin-left: 15; margin-right: 15; margin-top: 10'>

	<div style="text-align: center">
		<img src="CEVO_Header.png" style="height: 10%" />
	</div>

	<div>
		<h1>Please input your text here:</h1>
	</div>

	<div>
		<textarea style="border: 2; border-color: green; margin-bottom: 5;"
			autofocus="autofocus" rows="5" cols="30" id="InputText_Id"
			name="InputText" class="form-control">
		</textarea>

		<input type="button" class="btn btn-success" id="Btn_Annotate"
			value="Annotate" style='font-weight: bold; width: 100px'> <input
			type="button" class="btn btn-primary" id="Btn_Clear" value="Clear"
			style='font-weight: bold; width: 100px'>
	</div>
	<div class="displayResult" id="displayResult_Id"></div>

	<script>
		$("#Btn_Clear").click(function() 
			{
				$("#InputText_Id").val('');
				$("#table_Id").remove();
			});
	
		$("#Btn_Annotate").click(function() {

			var settings = {
				"async" : true,
				"crossDomain" : true,
				
				"url" : "http://eventontology.org:8080/api/",
				"method" : "POST",
				"headers" : {
					"content-type" : "text/plain"
					
				},
				"data" : $("#InputText_Id").val()
			}

			$.ajax(settings).done(function(response) {
				
				$("#table_Id").remove();
				if ($.trim(response))
					{
					var table = $('<table class="table table-striped" id="table_Id" style="border: solid; border-color: gray; margin-top: 5; margin-bottom: 0;"/>').appendTo($('#displayResult_Id'));
					$('<tr/>').appendTo(table)
						.append($('<th/>').text('Verb'))
						.append($('<th/>').text('Lemma'))
						.append($('<th/>').text('Begin Offset'))
						.append($('<th/>').text('End Offset'))
						.append($('<th/>').text('Event Label'))
						.append($('<th/>').text('Event URI'));
					
					
					$(response).each(function(i, resp) {
						
						var offsetLength = resp["verbOffsets"].length;
						var eventLength = resp["CEVOEvents"].length;
						for(j=0,k=0;j<offsetLength||k<eventLength;j++,k++)
						{
							if (j==0)
							{
							$('<tr style="height:15; font-size:15; border-collapse:separate; cellspacing:0; background-color:white; border-top:groove;"/>')
							.appendTo(table)
	                   		.append($('<td/>').text(resp["verb"]))
	                   		.append($('<td/>').text(resp["lemma"]))
	                   		.append($('<td/>').text(resp["verbOffsets"][j].beginOffset))
	                   		.append($('<td/>').text(resp["verbOffsets"][j].endOffset))
	                        .append($('<td/>').text(resp["CEVOEvents"][k].eventLabel))
	                        .append($('<td/>').append('<a href="'+resp["CEVOEvents"][k].eventURI+'">'+resp["CEVOEvents"][k].eventURI+'</a>'));
							
							}
							else if(j<offsetLength && k<eventLength)
								{
								$('<tr style="height:15; font-size:15; border-collapse:separate; cellspacing:0; background-color:white; border-top:hidden;"/>')
								.appendTo(table)
		                   		.append($('<td/>').text(''))
		                   		.append($('<td/>').text(''))
		                   		.append($('<td/>').text(resp["verbOffsets"][j].beginOffset))
		                   		.append($('<td/>').text(resp["verbOffsets"][j].endOffset))
		                        .append($('<td/>').text(resp["CEVOEvents"][k].eventLabel))
		                        .append($('<td/>').append('<a href="'+resp["CEVOEvents"][k].eventURI+'">'+resp["CEVOEvents"][k].eventURI+'</a>'));
								}
							else if(j>=offsetLength && k<eventLength)
								{
								$('<tr style="height:15; font-size:15; border-collapse:separate; cellspacing:0; background-color:white; border-top:hidden;"/>')
								.appendTo(table)
		                   		.append($('<td/>').text(''))
		                   		.append($('<td/>').text(''))
		                   		.append($('<td/>').text(''))
		                   		.append($('<td/>').text(''))
		                        .append($('<td/>').text(resp["CEVOEvents"][k].eventLabel))
		                        .append($('<td/>').append('<a href="'+resp["CEVOEvents"][k].eventURI+'">'+resp["CEVOEvents"][k].eventURI+'</a>'));
								}
							else if(j<offsetLength && k>=eventLength) {
								$('<tr style="height:15; font-size:15; border-collapse:separate; cellspacing:0; background-color:white; border-top:hidden;"/>')
								.appendTo(table)
		                   		.append($('<td/>').text(''))
		                   		.append($('<td/>').text(''))
		                   		.append($('<td/>').text(resp["verbOffsets"][j].beginOffset))
		                   		.append($('<td/>').text(resp["verbOffsets"][j].endOffset))
		                        .append($('<td/>').text(''))
		                        .append($('<td/>').text(''));
							}
						}
						
	                });
					}
				
				
			});

		});
	</script>

</body>
</html>