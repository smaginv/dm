package ru.smaginv.debtmanager.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.account.AccountInfoDto;
import ru.smaginv.debtmanager.web.dto.account.AccountUpdateDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = MappingUtil.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AccountMapper {

    @Mapping(target = "person", ignore = true)
    @Mapping(source = "type", target = "accountType")
    @Mapping(source = "currency", target = "currencyCode")
    @Mapping(source = "openDate", target = "openDate", qualifiedByName = "parseStringToLocalDateTime")
    @Mapping(source = "closedDate", target = "closedDate", qualifiedByName = "parseStringToLocalDateTime")
    Account map(AccountDto accountDto);

    @Mapping(source = "accountType", target = "type")
    @Mapping(source = "currencyCode", target = "currency")
    @Mapping(source = "openDate", target = "openDate", qualifiedByName = "formatDateToString")
    @Mapping(source = "closedDate", target = "closedDate", qualifiedByName = "formatDateToString")
    AccountDto mapDto(Account account);

    @Mapping(target = "operations", ignore = true)
    @Mapping(source = "accountType", target = "type")
    @Mapping(source = "currencyCode", target = "currency")
    @Mapping(source = "openDate", target = "openDate", qualifiedByName = "formatDateToString")
    @Mapping(source = "closedDate", target = "closedDate", qualifiedByName = "formatDateToString")
    AccountInfoDto mapInfoDto(Account account);

    List<AccountDto> mapDtos(List<Account> accounts);

    @Mapping(target = "accountType", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "currencyCode", ignore = true)
    @Mapping(target = "openDate", ignore = true)
    @Mapping(target = "closedDate", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "person", ignore = true)
    void update(AccountUpdateDto accountUpdateDto, @MappingTarget Account account);
}
