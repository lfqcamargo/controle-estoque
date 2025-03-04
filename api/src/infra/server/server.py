import logging
from flask import Flask, jsonify
from flask_cors import CORS
from src.infra.database.postgree.settings.connection import db_connection_handler
from src.infra.routes.company_routes import companies_route_bp
from src.infra.views.http_types.http_response import HttpResponse
from src.infra.middlewares.handle_errors import handle_errors


def create_app():
    """
    Init App
    """
    db_connection_handler.connect_to_db()

    app = Flask(__name__)
    CORS(app)

    logging.basicConfig(level=logging.DEBUG)
    app.logger.setLevel(logging.DEBUG)

    app.register_blueprint(companies_route_bp)

    @app.errorhandler(Exception)
    def global_error_handler(error):
        http_response = handle_errors(error)
        return jsonify(http_response.body), http_response.status_code

    return app
