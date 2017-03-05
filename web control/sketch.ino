int pins[] = {9, 11, 10, 6};

void setup() {
  for (int i = 0; i < 4; i++) {
    pinMode(pins[i], OUTPUT);
  }
  
  Serial.begin(9600);
}

int index = 0;

void loop() {
  if (Serial.available() > 0) {
    analogWrite(pins[index], Serial.read());
  }

  if (++index == 4) index = 0;
}
