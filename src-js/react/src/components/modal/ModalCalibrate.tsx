import axios from "axios";
import React, { useRef, useState } from "react";
import { Dropdown, Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { getDeviceFingerPrint } from "../../store/reducers/ActionCreators";
import ErrorPage from "../error-page/ErrorPage";
import Modal from "./Modal";
import "./Modal.css";

export default function ModalCalibrate({
  chargedKm,
  station,
}: {
  chargedKm: number;
  station: any;
}) {
  const [value, setValue] = useState<any>(null);
  const [error, setError] = useState<any>(null);
  const [calibratedKm, setCalibratedKm] = useState(null);
  // Math.round(chargedKm / 10) * 10;
  const roundChargedKm = 100;

  const kmArray = [
    10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170,
    180, 190, 200, 210, 220, 230, 240, 250, 260, 270, 280, 290, 300, 310, 320,
    330, 340, 350, 360, 370, 380, 390, 400, 410, 420, 430, 440, 450, 460, 470,
    480, 490, 500,
  ];

  const { t } = useTranslation();
  const btnStyle = value !== null ? "btnSend" : "disableBtn btnSend";

  const urlV2Calibrating = `${process.env.REACT_APP_LINK_SERVE}device/v2/station/calibrating?`;

  const calibrateResult = () => {
    const deviceFingerPrint = getDeviceFingerPrint();
    axios
      .get(
        `${urlV2Calibrating}station_number=${station}&device_finger_print=${deviceFingerPrint}&real_km=${value}`
      )
      .catch(function (error: any) {
        setError(error.message);
        console.log(error.message);
      })
      .then(function (result: any) {
        setCalibratedKm(result);
      });
  };

  const tryMore = () => {
    setError(null);
  };

  return (
    <Modal>
      <div className="calibrationCont">
        {error ? (
          <>
            <p className="calibrationText">{t("errorDevHeader")}</p>
            <div onClick={tryMore} className={btnStyle}>
              {t("btnRepeat")}
            </div>
          </>
        ) : (
          <>
            {calibratedKm ? (
              <div>
                <p className="calibrationSuccess">{t("chargedCongrats")}</p>
                <p className="calibrationSuccess">{t("calibratedKm")}</p>
              </div>
            ) : (
              <>
                <p className="calibrationTitle">{t("calibration")}</p>
                <p className="calibrationText">{t("enterYourKm")}:</p>
                {/* in telegram bootstrap dropdown */}
                <div className="selectBox">
                  {kmArray.map((n: number, index: any) => (
                    <div
                      className="listKm"
                      onClick={() => setValue(n)}
                      key={index}
                    >
                      {n}
                    </div>
                  ))}
                </div>

                <div onClick={calibrateResult} className={btnStyle}>
                  {t("sendKm")}
                </div>
              </>
            )}
          </>
        )}
      </div>
    </Modal>
  );
}
