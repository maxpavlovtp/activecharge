import axios from "axios";
import React, { useState } from "react";
import { styled } from "@mui/system";
import FormControl from "@mui/material/FormControl";
import { useTranslation } from "react-i18next";
import { getDeviceFingerPrint } from "../../store/reducers/ActionCreators";
import Modal from "./Modal";
import "./Modal.css";
import { MenuItem, Select } from "@mui/material";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { setModalOpen } from "../../store/reducers/FetchSlice";

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
  // chargedKm <= 10 ? 10 : Math.round(chargedKm / 10) * 10
  const roundChargedKm = chargedKm <= 10 ? 10 : Math.round(chargedKm / 10) * 10;

  const { isModalOpen } = useAppSelector((state) => state.fetchReducer);
  const dispatch = useAppDispatch();

  const kmArray = [
    10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170,
    180, 190, 200, 210, 220, 230, 240, 250, 260, 270, 280, 290, 300, 310, 320,
    330, 340, 350, 360, 370, 380, 390, 400, 410, 420, 430, 440, 450, 460, 470,
    480, 490, 500,
  ];

  const { t } = useTranslation();

  const btnStyle = value !== null ? "btnSend repeat" : "disableBtn btnSend";

  const urlV2Calibrating = `${process.env.REACT_APP_LINK_SERVE}device/v2/station/calibrating?`;

  const calibrateResult = () => {
    const deviceFingerPrint = getDeviceFingerPrint();
    setError("error");
    // axios
    //   .get(
    //     `${urlV2Calibrating}station_number=${station}&device_finger_print=${deviceFingerPrint}&real_km=${value}`
    //   )
    //   .catch(function (error: any) {
    //     setError(error.message);
    //     console.log(error.message);
    //   })
    //   .then(function (result: any) {
    //     setCalibratedKm(result);
    //   });
  };

  const setDefaultValue = (event: any) => {
    setValue(event.target.value);
  };

  const tryMore = () => {
    dispatch(setModalOpen(false));
  };

  return (
    <Modal>
      <div className="calibrationCont">
        {error ? (
          <>
            <p className="calibrationText">{t("featureInProgress")}</p>
            <div onClick={tryMore} className={btnStyle}>
              {t("close")}
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
                {isModalOpen && (
                  <FormControl size="small" fullWidth>
                    <Select
                      defaultOpen={true}
                      MenuProps={{
                        style: {
                          maxHeight: 250,
                        },
                      }}
                      variant="outlined"
                      onChange={setDefaultValue}
                      defaultValue={roundChargedKm}
                      sx={{
                        height: 40,
                      }}
                    >
                      {kmArray.map((n: number, index: any) => (
                        <MenuItem key={index} value={n}>
                          {n}
                        </MenuItem>
                      ))}
                    </Select>
                  </FormControl>
                )}

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
