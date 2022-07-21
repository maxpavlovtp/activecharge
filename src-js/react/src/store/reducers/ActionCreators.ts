import { AppDispatch } from "../store";
import axios from "axios";
import { FetchSlice } from "./FetchSlice";

const urlV2Start = `${process.env.REACT_APP_LINK_SERVE}device/v2/start`;
const urlV2Status = `${process.env.REACT_APP_LINK_SERVE}device/v2/status?id=`;

const period_s = process.env.REACT_APP_PERIOD_S;

export const idStart = () => async (dispatch: AppDispatch) => {
  let stationNumber = localStorage.getItem("stationNumber");
  const data = JSON.stringify({
    station_number: stationNumber,
    period_s: period_s,
  });

  const config = {
    method: "post",
    url: urlV2Start,
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    data: data,
  };
  dispatch(FetchSlice.actions.chargingDataFetching());
  await axios(config)
    .then(function (response: any) {
      localStorage.setItem("idDevice", response.data ? response.data.id : null);
      localStorage.setItem("interval", response.data ? response.data.scan_interval_ms : 2000);
      console.log(JSON.stringify(response.data));
      console.log(period_s)
      dispatch(FetchSlice.actions.chargingDataFetchingSuccess());
    })
    .catch(function (error: any) {
      console.log(error);
      dispatch(FetchSlice.actions.chargingDataFetchingError(error.message));
    });
};

export const getStationInfo = () => async (dispatch: AppDispatch) => {
  if (localStorage.getItem("idDevice") !== null) {
    try {
      dispatch(FetchSlice.actions.deviceStatusFetching());
      axios
        .get(urlV2Status + localStorage.getItem("idDevice"))
        .then(function (result: any) {
          dispatch(FetchSlice.actions.deviceStatusFetchingSuccess(result.data));
          localStorage.setItem("stationState", result.data.state)
          console.log(result.data);
        });
    } catch (e: any) {
      dispatch(FetchSlice.actions.deviceStatusFetchingError(e.message));
      console.log(e.message);
    }
  }
};
