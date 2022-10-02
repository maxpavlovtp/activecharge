import styled, { createGlobalStyle } from "styled-components";
import { Link } from "react-router-dom";
import { Col, Nav, Navbar } from "react-bootstrap";
import { AnimatePresence, motion } from "framer-motion";

export const GlobalStyles = createGlobalStyle`
  body {
    background: ${({ theme }: { theme: any }) => theme.body};
    color: ${({ theme }: { theme: any }) => theme.text};
    transition: all 0.5s linear;
  }
  `;

export const LinksColor = styled(Link)`
  color: ${({ theme }: { theme: any }) => theme.a};
  display: flex;
  text-decoration: none;
  &:hover {
    color: ${({ theme }: { theme: any }) => theme.a};
  }
  &:focus,
  &:hover,
  &:visited,
  &:link,
  &:active {
    text-decoration: none;
  }
  transition: all 0.5s linear;
`;

export const NavBar = styled(Navbar)`
  background-color: ${({ theme }: { theme: any }) => theme.navBarBg};
  transition: all 0.5s linear;
`;

export const NavLink = styled.div`
  color: ${({ theme }: { theme: any }) => theme.navText};
  display: flex;
  transition: all 0.5s linear;
`;

export const Translate = styled.button`
  background-color: ${({ theme }: { theme: any }) => theme.translateBg};
  color: ${({ theme }: { theme: any }) => theme.translateText};
  transition: all 0.5s linear;
`;

export const HomeCard = styled.div`
  background-color: ${({ theme }: { theme: any }) => theme.navBarBg};
  transition: all 0.5s linear;
`;

export const CardLink = styled(Link)`
  color: ${({ theme }: { theme: any }) => theme.a};
  text-decoration: none;
  &:hover {
    color: ${({ theme }: { theme: any }) => theme.a};
  }
  &:focus,
  &:hover,
  &:visited,
  &:link,
  &:active {
    text-decoration: none;
  }
  transition: all 0.5s linear;
`;

export const MainScreenLink = styled(Col)`
  color: ${({ theme }: { theme: any }) => theme.a};
  text-decoration: none;
  &:hover {
    color: ${({ theme }: { theme: any }) => theme.a};
  }
  &:focus,
  &:hover,
  &:visited,
  &:link,
  &:active {
    text-decoration: none;
  }
  transition: all 0.5s linear;
`;

export const PowerMetricsColor = styled.p`
  color: ${({ theme }: { theme: any }) => theme.powerMetricColor};
`;

export const OfferLinksColor = styled.a`
  color: ${({ theme }: { theme: any }) => theme.offerLinksColor};
  &:hover {
    color: ${({ theme }: { theme: any }) => theme.a};
  }
`;

export const FooterLink = styled.a`
  color: ${({ theme }: { theme: any }) => theme.footerColorLink};
`;

export const VoltageBtn = styled(Col)`
  color: ${({ theme }: { theme: any }) => theme.a};

  background-color: ${({ theme }: { theme: any }) => theme.navBarBg};
  transition: all 0.5s linear;
`;

export const FullInfoContainer = styled.div`
  background-color: ${({ theme }: { theme: any }) => theme.navBarBg};
  transition: all 0.5s linear;
`;

export const FinishKmStap = styled.span`
  color: ${({ theme }: { theme: any }) => theme.colorSpan};
  transition: all 0.5s linear;
`;

export const FinishKwtStap = styled.span`
  color: ${({ theme }: { theme: any }) => theme.text};
  transition: all 0.5s linear;
`;
