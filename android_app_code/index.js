// imports firebase-functions module
const functions = require('firebase-functions');
// imports firebase-admin module
const admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

var registrationToken = "**************************"




//admin.initializeApp(functions.config().firebase);
// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
 exports.firealarm = functions.database.ref('/noti').onWrite((change,context)=> {
    console.log('Push notification event triggered');
    /* Grab the current value of what was written to the Realtime Database */
  
    //console.log(event.params.pushId);
    console.log(change.after.val());
    
        var valueObject = change.after.val();
        console.log(valueObject.title);
    /*    
   
        const payload = {
            notification: {
                title: 'App Name',
                body: "New message",
                sound: "default"
            },
            data : {
                title: valueObject.title,
                msg: valueObject.msg
            }
            
        };
    
        const options = {
            priority: "high",
            timeToLive: 60 * 60 * 24 //24 hours
        };*/
        var topic = 'all';

// See documentation on defining a message payload.
        var message = {
            data: {
                title: valueObject.title,
                msg: valueObject.msg
            },
            topic: topic
        };
    return admin.messaging().send(message)
    .then((response) => {
      // Response is a message ID string.
      console.log('Successfully sent message:', response);
      console.log(response.results[0].error);
     console.log(JSON.stringify(response));
    })
    .catch((error) => {
      console.log('Error sending message:', error);
    });
    /*return admin.messaging().sendToDevice(registrationToken,payload,options)
    .then(function(response){
     console.log("successfully sent the message",response);
     console.log(response.results[0].error);
     console.log(JSON.stringify(response));
     
    })
    .catch(function(error){
        console.log("error to sent the message",error);
    });*/

  });


