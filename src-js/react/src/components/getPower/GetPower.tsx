import React, { useEffect, useState } from "react";
import "./GetPower.css";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { getStationInfo } from "../../store/reducers/ActionCreators";
import { useTranslation } from "react-i18next";
import { Col, Row } from "react-bootstrap";
import { PowerMetricsColor } from "../globalStyles";
import FullInfo from "../fullInfo/FullInfo";

export default function GetPower({
  station,
  timer,
}: {
  station: any;
  timer: any;
}) {
  const [chartTap, setChartTap] = useState(false);
  const [openInfo, setOpenInfo] = useState(false);
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

  let voltage = Number(Math.round(deviceStatus?.lastJob?.voltage));
  let isZero = deviceStatus?.lastJob?.chargedWtH === undefined || 0;

  let kWtCharged = Number(deviceStatus?.lastJob?.chargedWtH) / 1000;
  let kWtPower = Number(deviceStatus?.lastJob?.powerWt) / 1000;
  let chargeStatus = `${isZero ? " " : kWtCharged.toFixed(2)} ${t("wt")}`;
  let carKwtKmRatio = 200;

  const toggleMoreInfo = () => {
    setOpenInfo(!openInfo);
  };

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
          <PowerMetricsColor className="textTitle">
            {t("power")}
          </PowerMetricsColor>
          <p className="textTitle text">
            {(Number(kWtPower.toFixed(2)) * 1000) / Math.round(carKwtKmRatio)}{" "}
            {t("powerKm")}
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
            <p className="textTitle text">
              {isZero
                ? 0
                : Math.round((kWtCharged * 1000) / Math.round(carKwtKmRatio))}
              {t("km")}
            </p>
          </Col>
        )}
      </Row>
      {deviceStatus?.lastJob?.state === "DONE" ||
      deviceStatus?.lastJob?.state === "FAILED" ||
      deviceStatus?.lastJob?.leftS <= 3 ? (
        <></>
      ) : (
        <div
          style={{
            textAlign: "center",
            fontSize: "calc(1.5rem + 1.5vw)",
            margin: "25px 0 30px 0",
          }}
        >
          {timer}
        </div>
      )}
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
            onClick={toggleMoreInfo}
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

      {openInfo === true && (
        <FullInfo
          deviceStatus={deviceStatus}
          chartTap={chartTap}
          setChartTap={setChartTap}
          kWtPower={kWtPower}
          kWtCharged={kWtCharged}
          chargeStatus={chargeStatus}
        />
      )}
    </>
  );
}
