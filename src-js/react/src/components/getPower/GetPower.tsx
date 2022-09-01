import React, { useEffect, useState } from "react";
import "./GetPower.css";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { getStationInfo } from "../../store/reducers/ActionCreators";
import { useTranslation } from "react-i18next";
import { Col, Container, Row } from "react-bootstrap";
import { PowerMetricsColor } from "../globalStyles";

export default function GetPower({ station }: { station: any }) {
  const dispatch = useAppDispatch();
  const { deviceStatus } = useAppSelector((state) => state.fetchReducer);
  const { t } = useTranslation();
  const interval: any = localStorage.getItem("interval");
  const sec = interval ? interval : 5000;
  useEffect(() => {
    const timerID = setInterval(() => {
      if (deviceStatus?.state === "IN_PROGRESS") {
        dispatch(getStationInfo(station));
      }
    }, sec);
    return () => clearInterval(timerID);
  }, [deviceStatus?.state]);

  let kWtCharged = Number(deviceStatus?.chargedWt) / 1000;
  let kWtPower = Number(deviceStatus?.charginWt) / 1000;
  let voltage = Number(Math.round(deviceStatus?.voltage));

  let carKwtKmRatio = 150;
  let isZero = deviceStatus?.chargedWt === undefined;
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
            deviceStatus?.state === "DONE" ||
            deviceStatus?.state === "FAILED" ||
            deviceStatus?.leftS <= 3
              ? "offCont"
              : "text-center"
          }
        >
          <PowerMetricsColor className="mb-1 textTitle">{t("power")}</PowerMetricsColor>
          <p className="textTitle text">
            {kWtPower.toFixed(2)} {t("wt")}
          </p>
        </Col>

        {deviceStatus?.state === "DONE" ||
        deviceStatus?.state === "FAILED" ||
        deviceStatus?.leftS <= 3 ? (
          <Col xs="auto" lg="auto" className="text-center">
            <PowerMetricsColor className="finishTitle">{t("chargedCongrats")} </PowerMetricsColor>
            <p className="finishText">
              {t("chargedkWt")}
              {chargeStatus}
            </p>
          </Col>
        ) : (
          <Col className="text-center">
            <PowerMetricsColor className="mb-1 textTitle">{t("charging")}</PowerMetricsColor>
            <p className="textTitle text">{chargeStatus}</p>
          </Col>
        )}
      </Row>

      <Row className="justify-content-center mt-4">
        {deviceStatus?.state === "IN_PROGRESS" && (
          <Col
            xs="auto"
            className={
              deviceStatus?.state === "DONE" ||
              deviceStatus?.state === "FAILED" ||
              deviceStatus?.leftS <= 3
                ? "offCont"
                : "text-center mb-4"
            }
          >
            <PowerMetricsColor className="mb-1 textTitle voltTitle">{t("voltage")}</PowerMetricsColor>
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
    </>
  );
}
