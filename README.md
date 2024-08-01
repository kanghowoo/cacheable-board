### How to encrypt property
- ```
  $ mvn jasypt:encrypt-value \
  -Djasypt.encryptor.password="${your-password}" \
  -Djasypt.plugin.value="${your-value}"
  ```

- To use the plugin, just add the following to your pom.xml:
- 
  ```
  <build>
  <plugins>
    <plugin>
      <groupId>com.github.ulisesbocchio</groupId>
      <artifactId>jasypt-maven-plugin</artifactId>
      <version>3.0.5</version>
    </plugin>
  </plugins>
  </build>
  ```

### How to decrypt property (for debug)
- ```
  $ mvn jasypt:decrypt-value -Djasypt.encryptor.password="${your-password}" \
  -Djasypt.plugin.value="${encrypted-your-value}"
  ```
- You can check this all following docs.
    - [jasypt-spring-boot docs](https://github.com/ulisesbocchio/jasypt-spring-boot)

### How to Initialize Application
- [link](https://github.com/kanghowoo/maven_practice/blob/main/document/initialization.md)
