import React, { useEffect, useState } from "react";
import "./GetPower.css";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { getStationInfo } from "../../store/reducers/ActionCreators";
import { useTranslation } from "react-i18next";
import { Col, Row } from "react-bootstrap";
import {
  FinishKmStap,
  FinishKwtStap,
  PowerMetricsColor,
  VoltageBtn,
} from "../globalStyles";
import FullInfo from "../fullInfo/FullInfo";
import { setModalOpen } from "../../store/reducers/FetchSlice";
import ModalCalibrate from "../modal/ModalCalibrate";

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
  const sec = interval ? interval : 1000;
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
      <div className="mainBlock">
        <Row className="justify-content-center mb-1">
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
              deviceStatus?.lastJob?.state === "FAILED"
                ? "offCont"
                : "text-center"
            }
          >
            <PowerMetricsColor className="mb-1 textTitle">
              {t("power")}
            </PowerMetricsColor>
            <p className="textInfo text">
              {(
                (Number(Math.round(kWtPower)) * 1000) /
                Math.round(carKwtKmRatio)
              )}{" "}
              {t("powerKm")}
            </p>
          </Col>
          {deviceStatus?.lastJob?.state === "DONE" ||
          deviceStatus?.lastJob?.state === "FAILED" ? (
            <Col xs="auto" lg="auto" className="text-center">
              <PowerMetricsColor className="finishTitle">
                {t("chargedCongrats")}{" "}
              </PowerMetricsColor>
              <p className="finishText">
                {t("chargedkWt")}
                <br />
                <FinishKmStap
                  onClick={() => {
                    dispatch(setModalOpen(true));
                  }}
                  style={{ fontSize: "calc(1.7rem + 1.6vw)" }}
                >
                  {Math.round((kWtCharged * 1000) / Math.round(carKwtKmRatio))}{" "}
                  {t("km")}
                </FinishKmStap>
                <br />
                <FinishKwtStap style={{ fontSize: "calc(0.9rem + 1.3vw)" }}>
                  {chargeStatus}
                </FinishKwtStap>{" "}
              </p>
            </Col>
          ) : (
            <Col className="text-center">
              <PowerMetricsColor className="mb-1 textTitle">
                {t("charging")}
              </PowerMetricsColor>
              <p className="textInfo text">
                {isZero
                  ? 0
                  : Math.round((kWtCharged * 1000) / Math.round(carKwtKmRatio))}
                {t("km")}
              </p>
            </Col>
          )}
        </Row>
        {deviceStatus?.lastJob?.state === "DONE" ||
        deviceStatus?.lastJob?.state === "FAILED" ? (
          <></>
        ) : (
          <div
            style={{
              textAlign: "center",
              fontSize: "calc(1.5rem + 1.5vw)",
              margin: "15px 0 20px 0",
            }}
          >
            {timer}
          </div>
        )}
        <Row className="justify-content-center mt-4">
          {deviceStatus?.lastJob?.state === "IN_PROGRESS" && (
            <VoltageBtn
              xs="auto"
              style={
                openInfo === true
                  ? {
                      borderTopLeftRadius: "10px",
                      borderTopRightRadius: "10px",
                    }
                  : {
                      borderRadius: "10px",
                    }
              }
              className={
                deviceStatus?.lastJob?.state === "DONE" ||
                deviceStatus?.lastJob?.state === "FAILED"
                  ? "offCont"
                  : "text-center btnVoltage"
              }
              onClick={toggleMoreInfo}
            >
              <PowerMetricsColor className="mb-1 textTitle voltTitle">
                {t("voltage")}
              </PowerMetricsColor>
              <p className="voltTitle textInfo text">
                {voltage} {t("v")}
              </p>
            </VoltageBtn>
          )}
        </Row>
      </div>

      <FullInfo
        openInfo={openInfo}
        deviceStatus={deviceStatus}
        chartTap={chartTap}
        setChartTap={setChartTap}
        kWtPower={kWtPower}
        kWtCharged={kWtCharged}
        chargeStatus={chargeStatus}
      />
      <ModalCalibrate />
    </>
  );
}
