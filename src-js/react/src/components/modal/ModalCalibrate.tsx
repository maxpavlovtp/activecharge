import axios from "axios";
import React, { useState } from "react";
import { Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import ErrorPage from "../error-page/ErrorPage";
import Modal from "./Modal";
import "./Modal.css";

export default function ModalCalibrate() {
  const [value, setValue] = useState("");
  const [error, setError] = useState<any>(null);
  const [calibratedKm, setCalibratedKm] = useState(null);

  const handleChange = (event: any) => {
    const result = event.target.value.replace(/\D/g, "");
    setValue(result);
  };

  const { t } = useTranslation();
  const btnStyle = value !== "" ? "btnSend" : "disableBtn btnSend";

  const urlV2Calibrating = `${process.env.REACT_APP_LINK_SERVE}device/v2/station/calibrating?`;

  const calibrateResult = () => {
    axios
      .get(urlV2Calibrating + `real_km=${value}`)
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
                <Form>
                  <Form.Group controlId="formBasicEmail">
                    <Form.Control
                      onChange={handleChange}
                      type="text"
                      value={value}
                      placeholder="Enter km"
                    />
                  </Form.Group>
                  <div onClick={calibrateResult} className={btnStyle}>
                    {t("sendKm")}
                  </div>
                </Form>
              </>
            )}
          </>
        )}
      </div>
    </Modal>
  );
}
