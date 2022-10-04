import { Navigate, useSearchParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { useEffect } from "react";
import { getStationInfo } from "../../store/reducers/ActionCreators";

export default function PrivateRoute({ children }: { children: any }) {
  const dispatch = useAppDispatch();
  const { deviceStatus } = useAppSelector((state) => state.fetchReducer);
  const [searchParams] = useSearchParams();
  let stationNumbers: any = searchParams.get("station");

  useEffect(() => {
    dispatch(getStationInfo(stationNumbers));
    console.log(deviceStatus?.state);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  if (deviceStatus?.lastJob?.state === "IN_PROGRESS") {
    return <Navigate to={`/charging?station=${stationNumbers}`} />;
  } else if (
    deviceStatus?.lastJob?.state === "DONE" ||
    deviceStatus?.lastJobPresented === false
  ) {
    return children;
  }
}
