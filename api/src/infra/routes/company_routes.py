from flask import Blueprint, jsonify, request, Response

from src.infra.views.http_types.http_request import HttpRequest
from src.infra.composer.create_company_composer import create_company_composer

companies_route_bp = Blueprint("companies_routes", __name__)

@companies_route_bp.route("/companies", methods=["POST"])
def create_company() -> tuple[Response, int]:
    body = request.json
    http_request = HttpRequest(body=body)

    view = create_company_composer()

    http_response = view.handle(http_request)

    return jsonify(http_response.body), http_response.status_code
