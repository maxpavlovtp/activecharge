import React from "react";
import { Col, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { Chart } from "../charts/Chart";
import { PowerMetricsColor } from "../globalStyles";

export default function FullInfo({
  deviceStatus,
  chartTap,
  setChartTap,
  kWtCharged,
  kWtPower,
  chargeStatus,
}: {
  deviceStatus: any;
  chartTap: any;
  setChartTap: any;
  kWtCharged: any;
  kWtPower: any;
  chargeStatus: any;
}) {
  const { t } = useTranslation();
  return (
    <>
      <Row className="justify-content-center">
        <Col xs lg={6} className="text-center">
          <PowerMetricsColor className="mb-1 textTitle">
            {t("power")}
          </PowerMetricsColor>
          <p className="textTitle text">
            {kWtPower.toFixed(2)} {t("wt")}
          </p>
        </Col>

        <Col className="text-center">
          <PowerMetricsColor className="mb-1 textTitle">
            {t("charging")}
          </PowerMetricsColor>
          <p className="textTitle text">{chargeStatus}</p>
        </Col>
      </Row>
      <Row className="justify-content-center mb-4">
        <Chart
          chartTap={chartTap}
          setChartTap={setChartTap}
          leftS={deviceStatus?.lastJob?.leftS}
          power={Number(deviceStatus?.lastJob?.powerWt) / 1000}
          voltage={Number(Math.round(deviceStatus?.lastJob?.voltage))}
        />
      </Row>
    </>
  );
}
