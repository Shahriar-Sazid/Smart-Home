#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>

int leds[2] = {D3, D4};
int states[2] = {LOW, LOW};
int totalDevices = 2;

#define firebaseURl "dsdp-636be.firebaseio.com"

#define authCode "dLN2XMbJN9AjEIhFc5m1KJLhsFibpiMmeCcS3Etu"


#define wifiName "Munif"
#define wifiPass "12345678"

String chipId = "123";

bool on = false;

int change(int a)
{
    if (a == 1)
        return 0;
    else
        return 1;
}

void setupFirebase()
{
    Firebase.begin(firebaseURl, authCode);
}

void setupPinsMode()
{
    pinMode(leds[0], OUTPUT);
    pinMode(leds[1], OUTPUT);
}

void setupWifi()
{
    WiFi.begin(wifiName, wifiPass);
    Serial.println("Hey i'm connecting...");
    while (WiFi.status() != WL_CONNECTED)
    {
        Serial.println(".");
        delay(500);
    }
    Serial.println();
    Serial.println("I 'm connected and my IP address: ");
    Serial.println(WiFi.localIP());
}

void setup()
{
    Serial.begin(9600);
    pinMode(A0, INPUT);
    setupWifi();
    setupFirebase();
    setupPinsMode();
}

void getData()
{
    String path = chipId + "/status";
    FirebaseObject object = Firebase.get(path);
    
    int led1 = object.getInt("001");
    int led2 = object.getInt("002");
    
    Serial.println();
    Serial.println("Led 1: ");
    Serial.println(led1);
    Serial.println();
    Serial.println("Led 2: ");
    Serial.println(led2);

    // write output high or low to turn on or off led

    led1 = change(led1);

    if (led1 == 1)
        on = true;

    int light = analogRead(A0);

    if (on == false && light < 800)
    {
        change(led1);
    }

    digitalWrite(leds[0], led1);
    digitalWrite(leds[1], change(led2));
}

void loop()
{
    getData();
}