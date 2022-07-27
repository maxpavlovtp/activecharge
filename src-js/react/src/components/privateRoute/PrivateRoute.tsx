import { Navigate } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { useEffect } from "react";
import { getStationInfo } from "../../store/reducers/ActionCreators";

export default function PrivateRoute({ children }: { children: any }) {
  const dispatch = useAppDispatch();
  const { deviceStatus } = useAppSelector((state) => state.fetchReducer);

  useEffect(() => {
    dispatch(getStationInfo());
    console.log(deviceStatus?.state)
  }, []);

  return deviceStatus?.state === "IN_PROGRESS" ? <Navigate to="/charging" /> : children;
}
