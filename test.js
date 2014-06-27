var judge = new Array();
for(var i=0;i<5;i++)
		judge[i] = 0;
			


$('#0').find('.ratings_stars').hover(
				     function() {

					 if(judge[0] == 1)
					     {
						 judge[0]=0;
						 $(this).nextAll().removeClass('ratings_over');
					     }
					 if(judge[0] == 0){
					     $(this).prevAll().andSelf().addClass('ratings_over');
					     $(this).nextAll().removeClass('ratings_vote');
					     
					 }
				     },
				     // Handles the mouseout                                                                                                                            
				     function() {

					 if(judge[0] == 0)
					     {
						 $(this).prevAll().andSelf().removeClass('ratings_over');

					     }
				     }
				     )


    $('#1').find('.ratings_stars').hover(
					 function() {

					     if(judge[1] == 1)
						 {
						     judge[1]=0;
						     $(this).nextAll().removeClass('ratings_over');
						 }
					     if(judge[1] == 0){
						 $(this).prevAll().andSelf().addClass('ratings_over');
						 $(this).nextAll().removeClass('ratings_vote');

					     }
					 },

                                                                                                                                                       
					 function() {

					     if(judge[1] == 0)
						 {
						     $(this).prevAll().andSelf().removeClass('ratings_over');

						 }
					 }
					 )


    $('#2').find('.ratings_stars').hover(
					 function() {

					     if(judge[2] == 1)
						 {
						     judge[0]=0;
						     $(this).nextAll().removeClass('ratings_over');
						 }
					     if(judge[2] == 0){
						 $(this).prevAll().andSelf().addClass('ratings_over');
						 $(this).nextAll().removeClass('ratings_vote');

					     }
					 },

                                                                                                                                                       
					 function() {

					     if(judge[2] == 0)
						 {
						     $(this).prevAll().andSelf().removeClass('ratings_over');

						 }
					 }
					 )


    $('#3').find('.ratings_stars').hover(
					 function() {

					     if(judge[3] == 1)
						 {
						     judge[3]=0;
						     $(this).nextAll().removeClass('ratings_over');
						 }
					     if(judge[3] == 0){
						 $(this).prevAll().andSelf().addClass('ratings_over');
						 $(this).nextAll().removeClass('ratings_vote');

					     }
					 },
					 // Handles the
                                                                                                                                                       
					 function() {

					     if(judge[3] == 0)
						 {
						     $(this).prevAll().andSelf().removeClass('ratings_over');

						 }
					 }
					 )

    $('#4').find('.ratings_stars').hover(
					 function() {

					     if(judge[4] == 1)
						 {
						     judge[4]=0;
						     $(this).nextAll().removeClass('ratings_over');
						 }
					     if(judge[4] == 0){
						 $(this).prevAll().andSelf().addClass('ratings_over');
						 $(this).nextAll().removeClass('ratings_vote');

					     }
					 },
	
                                                                                                                                                       
					 function() {

					     if(judge[4] == 0)
						 {
						     $(this).prevAll().andSelf().removeClass('ratings_over');

						 }
					 }
					 )
$('#0').find('.ratings_stars').bind('click', function() {
        var star = this;
	var widget = $(this).parent();

	if(judge[0] == 0){
            var index = $(this).prevAll().length;

	}

	var vote = document.getElementById('vote0');
        vote.value = index+1;
	judge[0] = 1;

    });

$('#1').find('.ratings_stars').bind('click', function() {
	var star = this;
	var widget = $(this).parent();
	if(judge[1] == 0){
	    var index = $(this).prevAll().length;

	}

	var vote = document.getElementById('vote1');
	vote.value = index+1;
	judge[1] = 1;

    });

$('#2').find('.ratings_stars').bind('click', function() {
        var star = this;
	var widget = $(this).parent();
	if(judge[2] == 0){
            var index = $(this).prevAll().length;

	}

	var vote = document.getElementById('vote2');
        vote.value = index+1;
	judge[2] = 1;

    });

$('#3').find('.ratings_stars').bind('click', function() {
        var star = this;
	var widget = $(this).parent();
	if(judge[3] == 0){
            var index = $(this).prevAll().length;

	}

	var vote = document.getElementById('vote3');
        vote.value = index+1;
	judge[3] = 1;

    });

$('#4').find('.ratings_stars').bind('click', function() {
        var star = this;
	var widget = $(this).parent();

	if(judge[4] == 0){
            var index = $(this).prevAll().length;

	}

	var vote = document.getElementById('vote4');
        vote.value = index+1;
	judge[4] = 1;

    });



