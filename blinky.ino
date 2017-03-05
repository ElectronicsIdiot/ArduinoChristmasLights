int pins[] = {6, 9, 10, 11};
bool last[] = {false, false, false, false};
bool next[4];

void setup() {
}

void generate() {
  bool zero = true;
  
  for (int i = 0; i < 4; i++) {
    next[i] = (int) random(2);
    if (next[i]) zero = false;
  }

  if (zero) {
    next[(int) random(4)] = true;
  }
}

void loop() {
  for (boolean same = true; same;) {
    generate();

    for (int i = 0; i < 4; i++) {
      if (next[i] != last[i]) same = false;
    }
  }

  for (int i = 0; i < 256; i++) {
    for (int j = 0; j < 4; j++) {
      int value;

      if (next[j] == last[j]) continue;
      
      if (next[j] && !last[j]) value = i;

      if (!next[j] && last[j]) value = 255 - i;

      analogWrite(pins[j], value);
    }

    delayMicroseconds(1000);
  }

  for (int i = 0; i < 4; i++) {
    last[i] = next[i];
  }
}
