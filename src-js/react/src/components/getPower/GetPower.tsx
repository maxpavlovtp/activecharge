import React, { useEffect, useState } from "react";
import "./GetPower.css";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { getStationInfo } from "../../store/reducers/ActionCreators";
import { useTranslation } from "react-i18next";
import { Col, Container, Row } from "react-bootstrap";
import { PowerMetricsColor } from "../globalStyles";
import { Chart } from "../charts/Chart";

export default function GetPower({ station }: { station: any }) {
  const [chartTap, setChartTap] = useState(false);

  const dispatch = useAppDispatch();
  const { deviceStatus } = useAppSelector((state) => state.fetchReducer);
  const { t } = useTranslation();
  const interval: any = localStorage.getItem("interval");
  const sec = interval ? interval : 5000;
  useEffect(() => {
    if (chartTap === false) {
      const timerID = setInterval(() => {
        if (deviceStatus?.lastJob?.state === "IN_PROGRESS") {
          dispatch(getStationInfo(station));
        }
      }, sec);
      return () => clearInterval(timerID);
    }
  }, [deviceStatus?.lastJob?.state, chartTap]);

  let kWtCharged = Number(deviceStatus?.lastJob?.chargedWtH) / 1000;
  let kWtPower = Number(deviceStatus?.lastJob?.powerWt) / 1000;
  let voltage = Number(Math.round(deviceStatus?.lastJob?.voltage));

  let carKwtKmRatio = 150;
  let isZero = deviceStatus?.lastJob?.chargedWtH === undefined || 0;
  let chargeStatus = `${isZero ? " " : kWtCharged.toFixed(2)} ${t("wt")}`;

  return (
    <>
      <Row className="justify-content-center mb-4">
        <p className="stationText">
          {t("station")}: <span className="stationNumber">{station}</span>
        </p>
      </Row>

      <Row className="justify-content-center">
        <Col
          xs
          lg={6}
          className={
            deviceStatus?.lastJob?.state === "DONE" ||
            deviceStatus?.lastJob?.state === "FAILED" ||
            deviceStatus?.lastJob?.leftS <= 3
              ? "offCont"
              : "text-center"
          }
        >
          <PowerMetricsColor className="mb-1 textTitle">
            {t("power")}
          </PowerMetricsColor>
          <p className="textTitle text">
            {kWtPower.toFixed(2)} {t("wt")}
          </p>
        </Col>

        {deviceStatus?.lastJob?.state === "DONE" ||
        deviceStatus?.lastJob?.state === "FAILED" ||
        deviceStatus?.lastJob?.leftS <= 3 ? (
          <Col xs="auto" lg="auto" className="text-center">
            <PowerMetricsColor className="finishTitle">
              {t("chargedCongrats")}{" "}
            </PowerMetricsColor>
            <p className="finishText">
              {t("chargedkWt")}
              {chargeStatus}
            </p>
          </Col>
        ) : (
          <Col className="text-center">
            <PowerMetricsColor className="mb-1 textTitle">
              {t("charging")}
            </PowerMetricsColor>
            <p className="textTitle text">{chargeStatus}</p>
          </Col>
        )}
      </Row>

      <Row className="justify-content-center mt-4">
        {deviceStatus?.lastJob?.state === "IN_PROGRESS" && (
          <Col
            xs="auto"
            className={
              deviceStatus?.lastJob?.state === "DONE" ||
              deviceStatus?.lastJob?.state === "FAILED" ||
              deviceStatus?.lastJob?.leftS <= 3
                ? "offCont"
                : "text-center mb-4"
            }
          >
            <PowerMetricsColor className="mb-1 textTitle voltTitle">
              {t("voltage")}
            </PowerMetricsColor>
            <p className="voltTitle text">
              {voltage} {t("v")}
            </p>
          </Col>
        )}
      </Row>

      <Row className="justify-content-center">
        <Col xs="auto" className="text-center">
          <p className="kmCharged">
            {isZero
              ? 0
              : Math.round((kWtCharged * 1000) / Math.round(carKwtKmRatio))}
            {t("km")}
          </p>
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
