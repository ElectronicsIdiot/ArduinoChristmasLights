#define RED 11
#define GREEN 6
#define BLUE 9
#define YELLOW 10

void setup() {
  pinMode(RED, OUTPUT);
  pinMode(GREEN, OUTPUT);
  pinMode(BLUE, OUTPUT);
  pinMode(YELLOW, OUTPUT);
  
  Serial.begin(9600);
}

void loop() {
  if (Serial.available() > 0) {
    byte b = Serial.read();

    digitalWrite(RED, ((b & 0x01) == 0x01));
    digitalWrite(GREEN, ((b & 0x02) == 0x02));
    digitalWrite(BLUE, ((b & 0x04) == 0x04));
    digitalWrite(YELLOW, ((b & 0x08) == 0x08));
  }
}
