auth-service
Serves the basic functionality of authorization for bookstore services.

# Token generation
## Authorization code
1. Get auth code through web browser
   http://localhost:8044/oauth2/authorize?client_id=bookstore&redirect_uri=https://www.baidu.com&response_type=code&scope=userinfo.read
2. Get token via postman
   curl --location --request POST 'http://localhost:8044/oauth2/token' \
   --header 'Authorization: Basic Ym9va3N0b3JlOnNlY3JldA==' \
   --form 'grant_type="authorization_code"' \
   --form 'code="qSb-en5h20UnrZRmA3Jyf_4kka2uqMukVajsQCbzijj6kOEbtin6AgwAhQHPyj3uQGt1u0u88QittGFLqKTim51l7SX6-8rHmiz7q6M2X1827NgpeF7Nu28hGbBp13qR"' \
   --form 'redirect_uri="https://www.baidu.com"'
## Client credentials
   curl --location --request POST 'http://localhost:8044/oauth2/token' \
   --header 'accept:  application/json' \
   --header 'content-type: application/x-www-form-urlencoded' \
   --header 'Authorization: Basic Ym9va3N0b3JlOnNlY3JldA==' \
   --data-urlencode 'grant_type=client_credentials'
   
