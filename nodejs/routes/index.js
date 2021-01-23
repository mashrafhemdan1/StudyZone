// We now import the connection object we exported in db.js.
const db = require("../controllers/db");
const fb = require("../controllers/firebase.js");

const FirebaseApp = fb.FirebaseApp;
const FirebaseAuthentication = fb.FirebaseAuthentication;
// More libraries…
const express = require("express");
const bodyParser = require("body-parser");

const router = express.Router();

router.use(bodyParser.json()); // Automatically parse all POSTs as JSON.
router.use(bodyParser.urlencoded({ extended: true })); // Automatically parse URL parameters

router.use("/example_authenticated_api", FirebaseAuthentication); // <-- This one
// Skeleton for sendSMS POST request
router.post("/sendSMS", function (req, res) {
    let body = req.body; // let is like var, but scoped
   
    let phone = body.phone;
    let sms_body = body.message_body;

    let sql = `INSERT INTO sms (phone, body) VALUES ("` + phone + '", "' + sms_body + '")';
    db.query(sql, function (err) {
        console.log("Result: " + JSON.stringify(sql));
        if (err) {
            res.send(err);
        } else {
            res.send("OK");
        }
    });
});

// Skeleton for smsSent POST request
router.post("/smsSent", function (req, res) {
    let body = req.body; // let is like var, but scoped
   
    let id = body.msg_id;

    let sql = `UPDATE sms SET sent = true WHERE id = ` + id;
    db.query(sql, function (err) {
        console.log("Result: " + JSON.stringify(sql));
        if (err) {
            res.send(err);
        } else {
            res.end(JSON.stringify({ "response": "OK"}));
        }
    });
});

// Skeleton for getSMS GET Request
router.get("/getSMS", function (req, res) {
    let sql = `
        SELECT id, phone, body FROM sms WHERE sent = false ORDER BY ts LIMIT 1
    `;
    db.query(sql, function (err, result) {
        console.log("Result: " + JSON.stringify(sql));
        if (err) {
            res.send(err);
        } else {
            // Your code here 
            // You can use res.json(result); to send all data as a response 
            if(result.length > 0){
                return res.json(result[0]);
            }
            else {
                return res.end(JSON.stringify({}));
            }
        }
    });
});

// ---

// Hello World
router.get("/health", function (req, res) {
    return res.send("ok");
});

// Basic Addition POST request
router.post("/add", function (req, res) {
    let body = req.body; // let is like var, but scoped
   
    let num1 = body.num1;
    let num2 = body.num2;

    let result = num1 + num2;

    return res.json({
        "result": result
    });
});

// Basic SQL GET Request
router.get("/countrycodes", function (req, res) {
    let sql = "Select * from Country;"

    db.query(sql, function (err, result) {
        console.log("Result: " + JSON.stringify(result));
        if (err) {
            res.send(err);
        } else {
            let myResult = {
                "result": result,
                "rows": result.length
            };
            return res.send(myResult);
        }
    });
});

// Register Token
router.use("/register_notification_token", FirebaseAuthentication);
router.post("/register_notification_token", function (req, res) {
    let token = req.body.fcmToken;

    console.log(req.user.uid + " registered a device with token " + token);

    // You might want to add it to the database or something here. :)
    let sql = "INSERT INTO registery VALUES (\'" + req.user.uid + "\', \'" + token + "\') ON DUPLICATE KEY UPDATE token = \'" + token + "\';";

    db.query(sql, function (err, result) {
        console.log("Done inserted");
        if (err) {
            console.log("error: "+err);
            res.send(err);
            return;
        } else {
            console.log("no error now");
            return /*res.send("token registered")*/; 
        }
    });

    // Storing things in an object won't work long-term because the object
    // is in RAM and will be gone as soon as the server restarts.

    // Additionally, never use anything other than the UID to store a user's info
    // in your database. Emails, phone numbers and names can all change.

    return res.status(200).send("ok");
});

// Get Notification
router.use("/send_test_notification", FirebaseAuthentication);
router.post("/send_test_notification", function(req, res) {
    
    let token = "";

    let sql = "SELECT token FROM registery WHERE id = " + req.user.id + ";";

    db.query(sql, function (err, result) {
        console.log("Result: " + result.token);
        if (err) {
            res.send(err);
        } else {
            token = result.token;
        }
    });
    if (token === null || token === undefined) {
        return res.status(400).send("not registered");
    }

    console.log("Sending to token " + token);
    
    const message = {
        notification: {
            title: 'Your Request Notification',
            body: 'Hi (:'
        },
        token: token
    };

    FirebaseApp.messaging().sendAll(
        [message] // You can send multiple messages at the same time.
    ).then(result=> {
        console.log("Message sent successfully.");
        return res.status(200).send("ok");
    }).catch(err=> {
        console.error("Message failed to send:");
        console.error(err);
        return res.status(500).send("Internal Server Error");
    })
})


// Get Suggested Pairs
router.use("/getSuggestedPairs", FirebaseAuthentication);
router.post("/getSuggestedPairs", function(req, res) {
    let sql = `
    SELECT * FROM account WHERE userid  != 000001 AND userid NOT IN 
    (SELECT reciever_id as id FROM Pairs WHERE sender_id = ` + "000001 " /*req.user.id*/ + `
    UNION
    SELECT sender_id as id FROM Pairs WHERE reciever_id = ` + "000001 " /*req.user.id*/ + `)
    ;`;
    db.query(sql, function (err, result) {
        console.log("Result: " + JSON.stringify(sql));
        if (err) {
            res.send(err);
        } else {
            // Your code here 
            // You can use res.json(result); to send all data as a response 
            return res.json(result);
        }
    });
})

// Get Requests
router.post("/getRequests", function(req, res) {
    let sql = `
    SELECT * FROM pairs JOIN account ON userid = sender_id 
    WHERE reciever_id = ` + "000001" /*req.user.id*/ + ` AND status = 0;
    `;
    db.query(sql, function (err, result) {
        console.log("Result: " + JSON.stringify(sql));
        if (err) {
            res.send(err);
            return;
        } else {
            // Your code here 
            // You can use res.json(result); to send all data as a response 
            return res.json(result);
        }
    });
})

// Get My Pairs
router.use("/getMyPairs", FirebaseAuthentication);
router.post("/getMyPairs", function(req, res) {
    //let sql = "select FName, LName, university from account;";
    let sql = `
    SELECT * FROM Pairs JOIN account ON sender_id = userid
    WHERE reciever_id = ` + "000001" /*req.user.id*/ + ` AND status = 1
    UNION
    SELECT * FROM Pairs JOIN account ON reciever_id = userid
    WHERE sender_id = ` + "000001" /*req.user.id*/ + ` AND status = 1
    ;`;
    db.query(sql, function (err, result) {
        console.log("Result: " + JSON.stringify(sql));
        if (err) {
            res.send(err);
            return;
        } else {
            // Your code here 
            // You can use res.json(result); to send all data as a response 
            return res.json(result);
        }
    });
})

// Send Request
router.use("/sendRequest", FirebaseAuthentication);
router.post("/sendRequest", function(req, res) {
    //let sql = "select FName, LName, university from account;";
    let reciever_id = req.body.reciever_id;
    let sql = `
    INSERT INTO pairs VALUES (\'` + "000001" /*req.user.id*/ + `\', \'` + reciever_id + `\', 0)
    ;`;
    db.query(sql, function (err, result) {
        console.log("Result: " + JSON.stringify(sql));
        if (err) {
            res.send(err);
            return;
        } else {
            // Your code here 
            // You can use res.json(result); to send all data as a response 
            return res.end(JSON.stringify({"response": "done"}));
        }
    });
})

// Accept Request
router.use("/acceptRequest", FirebaseAuthentication);
router.post("/acceptRequest", function(req, res) {
    //let sql = "select FName, LName, university from account;";
    let sender_id = req.body.sender_id;
    let sql = `
    UPDATE pairs SET status = 1 WHERE sender_id = \'` + sender_id + `\' AND reciever_id = \'` + "000001" /*req.user.id*/ + `\'
    ;`;
    db.query(sql, function (err, result) {
        console.log("Result: " + JSON.stringify(sql));
        if (err) {
            res.send(err);
            return;
        } else {
            // Your code here 
            // You can use res.json(result); to send all data as a response 
            return res.end(JSON.stringify({"response": "done"}));
        }
    });
})

// Export the created router
module.exports = router;
