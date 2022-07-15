import { Navigate } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";

export default function PrivateRoute({ children }: { children: any }) {
  const { deviceStatus } = useAppSelector((state) => state.fetchReducer);
  const dispatch = useAppDispatch();
  let state: any = localStorage.getItem('stationState')

  console.log(deviceStatus); 
  return state === "IN_PROGRESS" ? (
    <Navigate to="/charging" />
  ) : (
    children
  );
}
