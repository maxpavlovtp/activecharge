import { AppDispatch } from "../store";
import axios from "axios";
import { FetchSlice } from "./FetchSlice";

const urlV2Start = `http://${process.env.REACT_APP_LINK_SERVE}device/v2/start`;
const urlV2Status = `http://${process.env.REACT_APP_LINK_SERVE}device/v2/status?id=`;


export const idStart = () => async (dispatch: AppDispatch) => {
  const data = JSON.stringify({
    station_number: "2",
    period_s: 60,
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
      localStorage.setItem("idDevice", response.data ? response.data : null);
      console.log(JSON.stringify(response.data));
    })
    .catch(function (error: any) {
      console.log(error);
      dispatch(FetchSlice.actions.chargingDataFetchingError(error.message));
    });
  dispatch(FetchSlice.actions.chargingDataFetchingSuccess());
};

export const getStationInfo = () => async (dispatch: AppDispatch) => {
  if (localStorage.getItem("idDevice") !== null) {
    try {
      dispatch(FetchSlice.actions.deviceStatusFetching());
      axios
        .get(urlV2Status + localStorage.getItem("idDevice"))
        .then(function (result: any) {
          dispatch(FetchSlice.actions.deviceStatusFetchingSuccess(result.data));
          console.log(result.data);
        });
    } catch (e: any) {
      dispatch(FetchSlice.actions.deviceStatusFetchingError(e.message));
      console.log(e.message);
    }
  }
};
