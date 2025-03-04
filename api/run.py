"""
This module initializes and runs the application server.

The server is created using the `create_app` function, which configures the Flask app. 
The app is set to run on '0.0.0.0' on port 3000 with debugging enabled.
"""

import os
from dotenv import load_dotenv

from src.infra.server.server import create_app

load_dotenv()

if __name__ == "__main__":
    app = create_app()
    app.run(host=os.getenv("HOST"), port=os.getenv("PORT"), debug=True)
