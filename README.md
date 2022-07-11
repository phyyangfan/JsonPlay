# JsonPlay
Gradle project (Java) for testing JSON and its utilities.

As of 2022-07-10, JSON object flattening logic has been implemented.

### Example

Input JSON
```json
{
  "Product Id": 123,
  "Product Name": "Some Product",
  "Attributes": {
    "Color": "blue",
    "Condition": "new"
  }
}
```
Flattened JSON
```json
{
  "Product Id": 123,
  "Product Name": "Some Product",
  "Attributes.Color": "blue",
  "Attributes.Condition": "new"
}
```

### Clone and Run

```shell
# clone the project
git clone https://github.com/phyyangfan/JsonPlay.git

# go to repo root
cd JsonPlay/

# build
./gradlew build

# copy the built files (in app.zip) to directory /some/temporary/directory
cp app/build/distributions/app.zip /some/temporary/directory

# go to directory /some/temporary/directory
cd /some/temporary/directory

# extract the built files
unzip app.zip

# go to the extracted app/bin/ directory
cd app/bin/

# run the program (assuming the input file test.json containing the JSON
# string exists in directory /some/path/)
./app < /some/path/test.json
# or
cat /some/path/test.json | ./app

# alternatively, run the program using keyboard input to provide the JSON string
./app #press Enter key
{
  "Product Id": 123,
  "Product Name": "Some Product",
  "Attributes": {
    "Color": "blue",
    "Condition": "new"
  }
}#press Enter key
#press ctrl+D
```
