import React from "react";
import { Col, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { Chart } from "../charts/Chart";
import { FullInfoContainer, PowerMetricsColor } from "../globalStyles";
import "../getPower/GetPower.css";
import { AnimatePresence, motion } from "framer-motion";

export default function FullInfo({
  openInfo,
  deviceStatus,
  chartTap,
  setChartTap,
  kWtCharged,
  kWtPower,
  chargeStatus,
}: {
  openInfo: any;
  deviceStatus: any;
  chartTap: any;
  setChartTap: any;
  kWtCharged: any;
  kWtPower: any;
  chargeStatus: any;
}) {
  const { t } = useTranslation();

  return (
    <AnimatePresence initial={false}>
      {openInfo === true && (
        <motion.div
          key="box"
          initial={{ height: 0 }}
          animate={{ height: "auto" }}
          exit={{
            height: 0,
            transition: { duration: 1, ease: "easeOut" },
          }}
          style={{ overflow: "hidden" }}
          transition={{ duration: 1 }}
        >
          <FullInfoContainer className="fullInfoCont">
            <Row className="justify-content-center mb-4">
              <Chart
                chartTap={chartTap}
                setChartTap={setChartTap}
                leftS={deviceStatus?.lastJob?.leftS}
                power={Number(deviceStatus?.lastJob?.powerWt) / 1000}
                voltage={Number(Math.round(deviceStatus?.lastJob?.voltage))}
              />
            </Row>
            <Row className="justify-content-center">
              <Col xs lg={6} className="text-center">
                <PowerMetricsColor className="mb-1 textTitle voltTitle">
                  {t("power")}
                </PowerMetricsColor>
                <p className="textTitle voltTitle text">
                  {kWtPower.toFixed(2)} {t("powerWt")}
                </p>
              </Col>

              <Col className="text-center mb-2">
                <PowerMetricsColor className="mb-1 textTitle voltTitle">
                  {t("charging")}
                </PowerMetricsColor>
                <p className="textTitle voltTitle text">{chargeStatus}</p>
              </Col>
            </Row>
          </FullInfoContainer>
        </motion.div>
      )}
    </AnimatePresence>
  );
}
