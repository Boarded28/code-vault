import requests, socket, sys, os

API_ENV="GEMINI_API_KEY"
BASE_URL="generativelanguage.googleapis.com"
VERSION="v1beta"
MODEL="gemini-3.8-flash"
METHOD="generateContent"

# check and validate command
args=sys.argv
if len(args) != 2:
    print("Error: Invalid command usage")
    print("Usage: hmm \"your prompt\"")
    sys.exit(1)
print("Valid argument")

# check env
API_KEY=os.environ.get(API_ENV)
if not API_KEY:
    print(f"\'{API_ENV}\' environment variable not found")
    print("Please set your environment variable and try again")
    sys.exit(1)
print("API key found")

# build url, header, payload
url=f"https://{BASE_URL}/{VERSION}/models/{MODEL}:{METHOD}"
headers={
    "Content-Type": "application/json",
    "X-goog-api-key": API_KEY,
}

data={
    "contents": [
        {
            "parts": [
                {
                    "text": args[1]
                }
            ]
        }
    ]
}
print(API_KEY)
print(f"Making a request to {url}...")

try:
    response = requests.post(url=url, headers=headers, json=data, timeout=5.0)
    parsed_json = response.json()

    if "error" in parsed_json:
        error_code = parsed_json["error"].get("code", "Unknown")
        error_mess = parsed_json["error"].get("message", "Message not provided")
        print(f"API Error {error_code}: {error_mess}")
        sys.exit(1)

    candidates = parsed_json.get("candidates", [])
    if candidates:
        text_output = candidates[0]["content"][0]["parts"][0]["text"]
        print(f"{text_output}")
    else:
        print("No content generated")
except requests.exceptions.Timeout:
    print("The request timed out. Check your connection")
    sys.exit(1)
except requests.exceptions.RequestException as e:
    print(f"An error has occured: {e}")
    sys.exit(1)