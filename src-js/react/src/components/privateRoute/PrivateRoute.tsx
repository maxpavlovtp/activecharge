import { Navigate } from "react-router-dom";

export default function PrivateRoute({ children }: { children: any }) {
  let state: any = localStorage.getItem("stationState");

  console.log(state);
  return state === "IN_PROGRESS" ? (
    <Navigate to="/charging" />
  ) : (
    children
  );
}
